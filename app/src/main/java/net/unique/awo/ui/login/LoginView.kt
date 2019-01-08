package net.unique.awo.ui.login

import net.unique.awo.base.BaseView
import net.unique.awo.model.LoginCreds

interface LoginView : BaseView {

    fun updateLoginCredentialsSuccess() {

    }

    fun getLoginCredentialsSuccess(loginCreds: LoginCreds)

}