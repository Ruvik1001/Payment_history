package com.example.paymenthistory.app

import android.app.Application
import com.example.paymenthistory.di.appModule
import com.example.paymenthistory.di.dataModule
import com.example.paymenthistory.di.domainModule
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.startKoin

/**
 * Custom Application class for initializing Koin dependency injection.
 */
class App : Application() {
    /**
     * Called when the application is starting. Responsible for initializing Koin modules.
     */
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                dataModule,
                domainModule
            )
        }
    }
}
