package com.example.demo_room.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ApplicationClass)
            modules(AppModules.modules)
        }
    }
}