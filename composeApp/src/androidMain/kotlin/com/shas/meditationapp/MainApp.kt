package com.shas.meditationapp

import android.app.Application
import com.shas.meditationapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class MainApp() : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApp)
        }
    }
}
