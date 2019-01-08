package net.unique.awo.ui.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import net.unique.awo.base.BasePresenter
import net.unique.awo.db.user.UserLocalRepo
import net.unique.awo.model.LoginCreds
import javax.inject.Inject

class LoginPresenter(loginView: LoginView) : BasePresenter<LoginView>(loginView) {

    @Inject
    lateinit var userLocalRepo: UserLocalRepo

    fun updateUserCreds(loginCreds: LoginCreds) {
        userLocalRepo.addUserCreds(loginCreds)

        view.updateLoginCredentialsSuccess()
    }

    fun getUserCreds() {
        userLocalRepo.getUserCreds()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<LoginCreds>() {
                override fun onComplete() {

                }

                override fun onNext(t: LoginCreds) {
                    view.updateLoginCredentialsSuccess()
                }

                override fun onError(e: Throwable) {

                }

            })

    }

}