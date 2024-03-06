package com.neillon.ticketmaster

import android.app.Application
import com.neillon.common.di.commonModule
import com.neillon.events.di.eventsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(eventsModule + commonModule)
        }
    }
}