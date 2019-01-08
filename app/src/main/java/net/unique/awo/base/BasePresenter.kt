package net.unique.awo.base

import net.unique.awo.di.component.DaggerAppComponent
import net.unique.awo.di.component.AppComponent
import net.unique.awo.di.module.ContextModule
import net.unique.awo.di.module.NetworkModule
import net.unique.awo.ui.login.LoginPresenter
import net.unique.awo.ui.post.PostPresenter

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 * @param V the type of the View the presenter is based on
 * @property view the view the presenter is based on
 * @constructor Injects the required dependencies
 * @property injector The injector used to inject required dependencies
 */
abstract class BasePresenter<out V : BaseView>(protected val view: V) {

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
            is LoginPresenter -> injector.inject(this)
        }
    }

}