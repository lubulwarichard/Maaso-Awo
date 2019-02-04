package net.unique.awo.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.Window
import net.unique.awo.R

/**
 * Activity all Activity classes of rosso must extend. It provides required methods and presenter
 * instantiation and calls.
 * @param P the type of the presenter the Activity is based on
 */
abstract class BaseActivity<P: BasePresenter<BaseView>> : BaseView, AppCompatActivity() {

    protected lateinit var presenter: P
    protected lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = instantiatePresenter()
    }

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter() : P

    override fun getContext(): Context {
        return this
    }

    fun showProgressDialog() {
        loadingDialog = Dialog(this)
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setCancelable(false)
        loadingDialog.setContentView(R.layout.loading_view)
        loadingDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show()
    }

    protected fun dismissProgressDilog() {
        loadingDialog.dismiss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

}