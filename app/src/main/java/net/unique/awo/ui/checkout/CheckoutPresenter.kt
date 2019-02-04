package net.unique.awo.ui.checkout

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import net.unique.awo.base.BasePresenter
import net.unique.awo.db.user.UserLocalRepo
import net.unique.awo.helpers.Constants
import net.unique.awo.helpers.Utils
import net.unique.awo.model.LoginCreds
import net.unique.awo.model.payment.MomoApiResponse
import net.unique.awo.model.payment.Payer
import net.unique.awo.model.payment.RequestPayment
import net.unique.awo.model.taxi.Driver
import net.unique.awo.remote.PostApi
import net.unique.awo.ui.login.AuthView
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject


class CheckoutPresenter(authView: AuthView.Checkout) : BasePresenter<AuthView.Checkout>(authView) {

    @Inject
    lateinit var mAuth: FirebaseAuth

    @Inject
    lateinit var mFirebaseDatabase: FirebaseDatabase

    fun getDriverDetails(driverId: String) {
        val myRef = mFirebaseDatabase.getReference("drivers").child(driverId)

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(Driver::class.java)
                Timber.v("Value is: %s", value)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Timber.e("Failed to read value. %s", error.message)
            }
        })
    }

    @SuppressLint("CheckResult")
    fun initiatePayment() {

        val payer = Payer(
            "MSISDN",
            "0777709243"
        )

        val requestPayment = RequestPayment(
            "2000",
            "EUR",
            "6725275785538538",
            payer,
            "Taxi payment"
        )
        postApi.requestPayment(requestPayment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Response<String>>(){
                override fun onComplete() {

                }

                override fun onNext(response: Response<String>) {
                    Timber.v("requestPayment result success:"+response.body().toString())
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Timber.e("requestPayment result error:"+e.localizedMessage)
                }

            })
    }

}