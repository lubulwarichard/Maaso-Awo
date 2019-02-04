package net.unique.awo.ui.login

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import net.unique.awo.base.BasePresenter
import net.unique.awo.db.user.UserLocalRepo
import net.unique.awo.helpers.Constants
import net.unique.awo.helpers.Utils
import net.unique.awo.model.LoginCreds
import net.unique.awo.model.payment.MomoApiResponse
import net.unique.awo.remote.PostApi
import okhttp3.Credentials
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import timber.log.Timber


class AuthPresenter(authView: AuthView.SmsVerify) : BasePresenter<AuthView.SmsVerify>(authView) {

    @Inject
    lateinit var mAuth: FirebaseAuth

    @Inject
    lateinit var userLocalRepoMock: UserLocalRepo

    fun updateUserCreds(loginCreds: LoginCreds) {
        userLocalRepoMock.updateUserCreds(loginCreds)

        view.updateLoginCredentialsSuccess(loginCreds)
    }

    fun loginUserServer(loginCreds: LoginCreds) {

        view.loginUserStarted()

        mAuth.signInWithEmailAndPassword(loginCreds.phoneNumber+"@gmail.com", loginCreds.phoneNumber)
            .addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.v("signInWithEmail:success")
                    val user = mAuth.currentUser

                    view.loginUserSuccess(user!!)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.e("""signInWithEmail:failure${task.exception}""")
                    if (task.exception.toString().contains("no user record")) {
                        createUserFirebase(loginCreds)
                    } else {
                        view.loginUserFailed("Authentication failed: "+task.exception!!.localizedMessage)
                    }
                }
            }
    }

    fun createUserFirebase(loginCreds: LoginCreds) {

        view.registerUserStarted()

        mAuth.createUserWithEmailAndPassword(loginCreds.phoneNumber+"@gmail.com", loginCreds.phoneNumber)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.v("createUserWithEmail:success")
                    val user = mAuth.currentUser

                    view.registerUserSuccess(user!!)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.e("""createUserWithEmail:failure${task.exception}""")

                    view.registerUserFailed("Authentication failed:"+task.exception!!.localizedMessage)
                }

            }
    }

}