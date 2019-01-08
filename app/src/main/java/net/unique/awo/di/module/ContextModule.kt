package net.unique.awo.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import net.unique.awo.base.BaseView
import javax.inject.Singleton

/**
 * Module which provides all required dependencies about Context
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
class ContextModule {

    /**
     * Provides the Context
     * @param baseView the BaseView used to provides the context
     * @return the Context to be provided
     */
    @Singleton
    @Provides
    internal fun provideContext(baseView: BaseView): Context {
        return baseView.getContext()
    }

    /**
     * Provides the Application Context
     * @param context Context in which the application is running
     * @return the Application Context to be provided
     */
    @Singleton
    @Provides
    internal fun provideApplication(context: Context): Application {
        return context.applicationContext as Application
    }

}