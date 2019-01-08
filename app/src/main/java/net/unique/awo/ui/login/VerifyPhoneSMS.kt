package net.unique.awo.ui.login

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_verify_phone_sms.*
import net.unique.awo.R
import net.unique.awo.base.BaseActivity
import net.unique.awo.model.LoginCreds

class VerifyPhoneSMS : BaseActivity<LoginPresenter>(), LoginView {

    private lateinit var passed_login_creds: LoginCreds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone_sms)

        initStuff()
        setupViews()
    }

    private fun initStuff() {
        passed_login_creds = intent.getSerializableExtra("passed_login_creds") as LoginCreds
    }

    private fun setupViews() {
        btn_verify_sms.setOnClickListener {
            presenter.updateUserCreds(passed_login_creds)
        }
    }

    override fun updateLoginCredentialsSuccess() {

    }

    override fun getLoginCredentialsSuccess(loginCreds: LoginCreds) {

    }

    override fun instantiatePresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

}
