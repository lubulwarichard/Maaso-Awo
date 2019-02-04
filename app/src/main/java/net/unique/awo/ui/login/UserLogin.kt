package net.unique.awo.ui.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_user_login.*
import net.unique.awo.R
import net.unique.awo.model.LoginCreds

class UserLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        setupToolbar()
        setupViews()

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViews() {

        btn_verify_sms.setOnClickListener {

            val phone_number = et_entered_phone_sms.text.toString()
            val country_name = cp_picker.selectedCountryName
            val country_code = cp_picker.selectedCountryCode

            if (phone_number.length == 9) {

                val loginCreds = LoginCreds(country_code, country_name, "", "", phone_number)

                val intent = Intent(this, VerifyPhoneSMS::class.java)
                intent.putExtra("passed_login_creds", loginCreds)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_LONG).show()
            }

        }

    }

}