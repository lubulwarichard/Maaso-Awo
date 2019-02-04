package net.unique.awo

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import timber.log.Timber

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        //Enable Timber
        Timber.DebugTree()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Timber.plant(Timber.DebugTree());
    }

}