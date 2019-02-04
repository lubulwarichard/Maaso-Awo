package net.unique.awo.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_verify_phone_sms.*
import net.unique.awo.R
import net.unique.awo.base.BaseActivity
import net.unique.awo.model.LoginCreds
import net.unique.awo.ui.splash.SplashActivity

class VerifyPhoneSMS : BaseActivity<AuthPresenter>(), AuthView.SmsVerify {

    private lateinit var passed_login_creds: LoginCreds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone_sms)

        setupToolbar()
        initStuff()
        setupViews()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initStuff() {
        passed_login_creds = intent.getSerializableExtra("passed_login_creds") as LoginCreds
    }

    private fun setupViews() {
        btn_verify_sms.setOnClickListener {
            presenter.updateUserCreds(passed_login_creds)
        }
    }

    override fun instantiatePresenter(): AuthPresenter {
        return AuthPresenter(this)
    }

    /**
     * Adding data to local database
     */

    override fun updateLoginCredentialsSuccess(loginCreds: LoginCreds) {
        presenter.loginUserServer(loginCreds)
    }

    override fun updateLoginCredentialsFailed(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    /**
     * Signing in
     */

    override fun loginUserStarted() {

    }

    override fun loginUserSuccess(user: FirebaseUser) {
        startActivity(Intent(this@VerifyPhoneSMS, SplashActivity::class.java))
        finish()
    }

    override fun loginUserFailed(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    /**
     * Registration
     */

    override fun registerUserStarted() {

    }

    override fun registerUserSuccess(user: FirebaseUser) {
        startActivity(Intent(this@VerifyPhoneSMS, SplashActivity::class.java))
        finish()
    }

    override fun registerUserFailed(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

}
