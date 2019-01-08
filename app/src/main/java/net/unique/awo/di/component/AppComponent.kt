package net.unique.awo.di.component

import dagger.BindsInstance
import dagger.Component
import net.unique.awo.base.BaseView
import net.unique.awo.di.module.ContextModule
import net.unique.awo.di.module.DatabaseModule
import net.unique.awo.di.module.NetworkModule
import net.unique.awo.ui.login.LoginPresenter
import net.unique.awo.ui.post.PostPresenter
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class), (DatabaseModule::class)])
interface AppComponent {

    /**
     * Injects required dependencies into the specified PostPresenter.
     * @property postPresenter PostPresenter in which to inject the dependencies
     * @property loginPresenter LoginPresenter in which to inject the dependencies
     */
    fun inject(postPresenter: PostPresenter)
    fun inject(loginPresenter: LoginPresenter)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder
        fun databaseModule(databaseModule: DatabaseModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }

}