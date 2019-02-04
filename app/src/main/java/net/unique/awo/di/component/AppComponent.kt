package net.unique.awo.di.component

import dagger.BindsInstance
import dagger.Component
import net.unique.awo.base.BaseView
import net.unique.awo.di.module.ContextModule
import net.unique.awo.di.module.DatabaseModule
import net.unique.awo.di.module.FirebaseModule
import net.unique.awo.di.module.NetworkModule
import net.unique.awo.ui.checkout.CheckoutPresenter
import net.unique.awo.ui.login.AuthPresenter
import net.unique.awo.ui.post.PostPresenter
import net.unique.awo.ui.splash.SplashPresenter
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class), (DatabaseModule::class), (FirebaseModule::class)])
interface AppComponent {

    /**
     * Injects required dependencies into the specified PostPresenter.
     * @property postPresenter PostPresenter in which to inject the dependencies
     * @property loginPresenter AuthPresenter in which to inject the dependencies
     * @property splashPresenter SplashPresenter in which to inject the dependencies
     * @property checkoutPresenter CheckoutPresenter in which to inject the dependencies
     */
    fun inject(postPresenter: PostPresenter)
    fun inject(authPresenter: AuthPresenter)
    fun inject(splashPresenter: SplashPresenter)
    fun inject(checkoutPresenter: CheckoutPresenter)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder
        fun databaseModule(databaseModule: DatabaseModule): Builder
        fun firebaseModule(firebaseModule: FirebaseModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }

}