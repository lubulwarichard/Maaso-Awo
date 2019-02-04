package net.unique.awo.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import net.unique.awo.R
import net.unique.awo.base.BaseActivity
import net.unique.awo.model.LoginCreds
import net.unique.awo.model.payment.MomoApiResponse
import net.unique.awo.ui.home.Main2Activity
import net.unique.awo.ui.login.AuthView
import net.unique.awo.ui.login.UserLogin
import timber.log.Timber

class SplashActivity : BaseActivity<SplashPresenter>(), AuthView.Splash {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            checkIfLoggedIn()
        }, 3000) //3 seconds

    }

    private fun checkIfLoggedIn() {
        presenter.initiateGetUserCreds()
    }

    private fun goToMainActivity() {
        startActivity(Intent(this@SplashActivity, Main2Activity::class.java))
        finish()
    }

    override fun getLoginCredentialsSuccess(loginCreds: LoginCreds) {
        // Timber.e("Login credentials: $loginCreds")
        if (loginCreds.userReferenceId.isNotEmpty() && loginCreds.userApiKey.isNotEmpty()) {
            goToMainActivity()
        } else {
            presenter.getMomoApiToken(loginCreds)
        }
    }

    override fun getLoginCredentialsFailed(errorMessage: String) {
        startActivity(Intent(this@SplashActivity, UserLogin::class.java))
        finish()
    }

    override fun instantiatePresenter(): SplashPresenter {
        return SplashPresenter(this)
    }

    override fun getMomoTokenSuccess(tokenResponse: MomoApiResponse.TokenResponse) {
        goToMainActivity()
    }

    override fun getMomoTokenFailed(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        goToMainActivity()
    }


}
