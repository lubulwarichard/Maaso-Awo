package net.unique.awo.base

import android.content.Context
import net.unique.awo.model.LoginCreds
import net.unique.awo.model.payment.MomoApiResponse

/**
 * Base view any view must implement.
 */
public interface BaseView {
    /**
     * Returns the context in which the application is running.
     * @return the context in which the application is running
     */
    fun getContext() : Context

    fun getLoginCredentialsSuccess(loginCreds: LoginCreds){}
    fun getLoginCredentialsFailed(errorMessage: String){}

    /**
     * Momo Api views
     */
    fun getMomoTokenSuccess(tokenResponse: MomoApiResponse.TokenResponse){}
    fun getMomoTokenFailed(errorMessage: String){}

}