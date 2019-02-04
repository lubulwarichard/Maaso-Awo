package net.unique.awo.ui.checkout

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_checkout.*
import net.unique.awo.R
import net.unique.awo.base.BaseActivity
import net.unique.awo.model.LoginCreds
import net.unique.awo.ui.login.AuthView


class CheckoutActivity : BaseActivity<CheckoutPresenter>(), AuthView.Checkout {

    private lateinit var passed_driver_id: String

    override fun instantiatePresenter(): CheckoutPresenter {
        return CheckoutPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        setupToolbar()
        initStuff()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initStuff() {
        passed_driver_id = intent.getStringExtra("driver_id") as String

        presenter.getDriverDetails(passed_driver_id)

        btn_pay_now.setOnClickListener {
            preparePayment()
        }
    }

    private fun preparePayment() {
        // presenter.initiatePayment()
        //presenter.getUserCreds(userLocalRepo)
    }

    override fun getDriverDetailsStarted() {

    }

    override fun getDriverDetailsSuccess() {

    }

    override fun getDriverDetailsFailed(errorMessage: String) {

    }

    override fun getUserCredentialsSuccess(loginCreds: LoginCreds) {

    }

    override fun getUserCredentialsFailed(errorMessage: String) {

    }

}
