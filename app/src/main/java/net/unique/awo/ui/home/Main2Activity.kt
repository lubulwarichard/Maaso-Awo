package net.unique.awo.ui.home

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.pay_input_dialog.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import net.unique.awo.R
import net.unique.awo.ui.checkout.CheckoutActivity
import timber.log.Timber


class Main2Activity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private val FLASH_STATE = "FLASH_STATE"

    private lateinit var mScannerView: ZXingScannerView
    private var mFlash: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        setupToolbar()

        val contentFrame = findViewById<View>(R.id.content_frame) as ViewGroup
        mScannerView = ZXingScannerView(this)
//        mScannerView = object : ZXingScannerView(this) {
//            override fun createViewFinderView(context: Context): IViewFinder {
//                val finderView = ViewFinderView(context)
//                finderView.setLaserColor(Color.GREEN)
//                finderView.setMaskColor(Color.RED)
//                return finderView
//            }
//        }
        contentFrame.addView(mScannerView)

        // showResultDialog("1")
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    public override fun onResume() {
        super.onResume()

        mScannerView.setResultHandler(this)
        // You can optionally set aspect ratio tolerance level
        // that is used in calculating the optimal Camera preview size
        mScannerView.setAspectTolerance(0.5f)
        mScannerView.setAutoFocus(true)
        mScannerView.startCamera()
        mScannerView.flash = mFlash
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(FLASH_STATE, mFlash)
    }

    override fun handleResult(rawResult: Result) {
        Timber.v("Contents = " + rawResult.text + ", Format = " + rawResult.barcodeFormat.toString())

        showResultDialog(rawResult.text)

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        val handler = Handler()
        handler.postDelayed({ mScannerView.resumeCameraPreview(this@Main2Activity) }, 2000)

    }

    fun toggleFlash(v: View) {
        mFlash = !mFlash
        mScannerView.flash = mFlash
    }

    private fun showResultDialog(driverId: String) {
        mScannerView.stopCameraPreview()

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pay_input_dialog)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        val pay_amount = dialog.findViewById(R.id.et_pay_amount) as EditText
        dialog.btn_pay_amount_confirm.setOnClickListener {
            if (pay_amount.text.toString().length > 2) {
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtra("driver_id", driverId)
                intent.putExtra("pay_amount", pay_amount.text.toString())
                startActivity(intent)
            }
        }
        dialog.iv_close_dialog.setOnClickListener {
            dialog.dismiss()
            mScannerView.resumeCameraPreview(this@Main2Activity)
        }

        dialog.show()

    }

}
