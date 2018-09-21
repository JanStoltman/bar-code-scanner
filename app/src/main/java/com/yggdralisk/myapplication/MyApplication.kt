package com.yggdralisk.myapplication

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor.Level
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeFastNetworking()
        plantTimber()
    }

    private fun plantTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initializeFastNetworking() {
        AndroidNetworking.initialize(applicationContext)
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(Level.BODY)
        }
    }
}