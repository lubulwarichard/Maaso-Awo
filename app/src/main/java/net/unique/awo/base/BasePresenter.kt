package net.unique.awo.base

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import net.unique.awo.R
import net.unique.awo.db.user.UserLocalRepo
import net.unique.awo.di.component.DaggerAppComponent
import net.unique.awo.di.component.AppComponent
import net.unique.awo.di.module.ContextModule
import net.unique.awo.di.module.NetworkModule
import net.unique.awo.helpers.Constants
import net.unique.awo.helpers.Utils
import net.unique.awo.model.LoginCreds
import net.unique.awo.model.payment.MomoApiResponse
import net.unique.awo.remote.PostApi
import net.unique.awo.ui.checkout.CheckoutPresenter
import net.unique.awo.ui.login.AuthPresenter
import net.unique.awo.ui.post.PostPresenter
import net.unique.awo.ui.splash.SplashPresenter
import okhttp3.Credentials
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 * @param V the type of the View the presenter is based on
 * @property view the view the presenter is based on
 * @constructor Injects the required dependencies
 * @property injector The injector used to inject required dependencies
 */
abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    @Inject
    lateinit var userLocalRepo: UserLocalRepo

    @Inject
    lateinit var postApi: PostApi

    /**
     * The injector used to inject required dependencies
     */
    private val injector: AppComponent = DaggerAppComponent
        .builder()
        .baseView(view)
        .contextModule(ContextModule())
        .networkModule(NetworkModule())
        .build()

    init {
        inject()
    }

    /**
     * This method may be called when the presenter view is created
     */
    open fun onViewCreated(){}

    /**
     * This method may be called when the presenter view is destroyed
     */
    open fun onViewDestroyed(){}

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is PostPresenter -> injector.inject(this)
            is AuthPresenter -> injector.inject(this)
            is SplashPresenter -> injector.inject(this)
            is CheckoutPresenter -> injector.inject(this)
        }
    }

    @SuppressLint("CheckResult")
    fun getUserCreds() {

        userLocalRepo.getUserCreds()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<LoginCreds>() {
                override fun onComplete() {

                }

                override fun onNext(loginCreds: LoginCreds) {
                    Constants.MOMO_REFERENCE_ID = loginCreds.userReferenceId
                    Constants.MOMO_USER_API_KEY = loginCreds.userApiKey

                    view.getLoginCredentialsSuccess(loginCreds)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    view.getLoginCredentialsFailed(e.localizedMessage)
                }

            })

    }


    /**
     *  Momo Api methods
     */
    fun getMomoApiToken(loginCreds: LoginCreds) {

        if (Constants.MOMO_REFERENCE_ID.isEmpty() || Constants.MOMO_USER_API_KEY.isEmpty()) {
            createUser(loginCreds)
        } else {
            initiateTokenRequest()
        }

    }

    @SuppressLint("CheckResult")
    fun createUser(loginCreds: LoginCreds) {

        val params = HashMap<String, Any>()
        params["providerCallbackHost"] = ""

        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(params).toString()
        )

        val uuid = Utils.generateUUID()
        postApi.createUser(uuid, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object  : DisposableObserver<Response<Void>>(){
                override fun onComplete() {

                }

                override fun onNext(response: Response<Void>) {
                    if (response.code() == 201) {
                        loginCreds.userReferenceId = uuid
                        userLocalRepo.updateUserCreds(loginCreds)

                        Constants.MOMO_REFERENCE_ID = loginCreds.userReferenceId

                        getApiKey(loginCreds)
                    } else {

                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            })
    }

    @SuppressLint("CheckResult")
    fun getApiKey(loginCreds: LoginCreds) {
        postApi.getApiKey(Constants.MOMO_REFERENCE_ID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Response<MomoApiResponse>>(){
                override fun onComplete() {

                }

                override fun onNext(response: Response<MomoApiResponse>) {
                    Timber.v("getApiKey result success:"+response.body().toString())
                    if (response.body() != null) {
                        loginCreds.userApiKey = response.body()!!.apiKey
                        userLocalRepo.updateUserCreds(loginCreds)

                        Constants.MOMO_USER_API_KEY = response.body()!!.apiKey

                        initiateTokenRequest()
                    }
                }

                override fun onError(e: Throwable) {
                    Timber.e("getApiKey result error:"+e.localizedMessage)
                }

            })
    }

    @SuppressLint("CheckResult")
    private fun initiateTokenRequest() {

        val credentials = Credentials.basic(Constants.MOMO_REFERENCE_ID, Constants.MOMO_USER_API_KEY);

        postApi.getToken(credentials)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Response<MomoApiResponse.TokenResponse>>(){
                override fun onComplete() {

                }

                override fun onNext(response: Response<MomoApiResponse.TokenResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        Timber.v("initiateTokenRequest result success:"+response.body().toString())

                        Constants.MOMO_USER_TOKEN = response.body()!!.access_token
                        Constants.MOMO_USER_TOKEN_EXPIRY = response.body()!!.expires_in
                        view.getMomoTokenSuccess(response.body()!!)
                    } else {
                        Timber.v("initiateTokenRequest result success1:"+response.body().toString())
                        view.getMomoTokenFailed("Something went wrong, please try again later…")
                    }
                }

                override fun onError(e: Throwable) {
                    Timber.e("initiateTokenRequest result error:"+e.localizedMessage)
                    view.getMomoTokenFailed(e.localizedMessage)
                }

            })
    }

}