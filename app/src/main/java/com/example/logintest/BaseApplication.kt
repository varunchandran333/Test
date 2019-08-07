package com.example.logintest

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class BaseApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: BaseApplication? = null

        fun getContext(): Context {
            return instance!!.applicationContext
        }

//        fun getSharedPreferences(): SharedPreferences {
//            return getContext().getSharedPreferences(BusConstants.SHARED_PREFFRENCE_UNIQUE_ID, Context.MODE_PRIVATE)
//        }

    }
}