package com.jaws.challengeappilham

import android.app.Application
import android.content.Context
import com.jaws.challengeappilham.data.local.database.AppDatabase
import com.jaws.challengeappilham.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    init{
        instance = this
    }

    companion object{
        private var instance: App? = null
        fun appContext(): Context = instance!!.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
        initKoin()
    }

    private fun initKoin(){
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.modules)
        }
    }
}