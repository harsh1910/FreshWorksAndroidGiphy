package com.freshworks.freshworksandroidgiphy

import android.app.Application

class FreshWorksAndroidGiphyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: FreshWorksAndroidGiphyApplication
    }

}