package com.jaws.challengeappilham

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
//        initKoin()
    }

//    private fun initKoin() {
//        startKoin {
//            androidLogger()
//            androidContext(this@App)
//            modules(AppModules.modules)
//        }
//    }
}
