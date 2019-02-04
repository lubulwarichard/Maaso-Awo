package net.unique.awo.ui.splash

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import net.unique.awo.base.BasePresenter
import net.unique.awo.db.user.UserLocalRepo
import net.unique.awo.helpers.Constants
import net.unique.awo.model.LoginCreds
import net.unique.awo.ui.login.AuthView
import javax.inject.Inject

class SplashPresenter(authView: AuthView.Splash) : BasePresenter<AuthView.Splash>(authView) {

    fun initiateGetUserCreds(){
        getUserCreds()
    }

}