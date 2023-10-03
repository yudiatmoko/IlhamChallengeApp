package com.jaws.challengeappilham

import android.app.Application
import com.jaws.challengeappilham.data.local.database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}