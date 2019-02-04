package net.unique.awo.ui.login

import com.google.firebase.auth.FirebaseUser
import net.unique.awo.base.BaseView
import net.unique.awo.model.LoginCreds

interface AuthView : BaseView {

    interface SmsVerify: BaseView {
        fun updateLoginCredentialsSuccess(loginCreds: LoginCreds)
        fun updateLoginCredentialsFailed(errorMessage: String)

        fun loginUserStarted()
        fun loginUserSuccess(user: FirebaseUser)
        fun loginUserFailed(errorMessage: String)

        fun registerUserStarted()
        fun registerUserSuccess(user: FirebaseUser)
        fun registerUserFailed(errorMessage: String)
    }

    interface Splash: BaseView {
//        fun getLoginCredentialsSuccess1(loginCreds: LoginCreds)
//        fun getLoginCredentialsFailed1(errorMessage: String)
    }

    interface Checkout: BaseView {
        abstract fun getUserCredentialsSuccess(loginCreds: LoginCreds)
        abstract fun getUserCredentialsFailed(errorMessage: String)

        fun getDriverDetailsStarted()
        fun getDriverDetailsSuccess()
        fun getDriverDetailsFailed(errorMessage: String)
    }

}