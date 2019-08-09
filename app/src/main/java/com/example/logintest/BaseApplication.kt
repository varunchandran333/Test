package com.example.logintest

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(getContext())
    }
    init {
        mInstance = this
    }

    companion object {
        lateinit var mInstance: BaseApplication
        fun getContext(): Context = mInstance.applicationContext
    }
}