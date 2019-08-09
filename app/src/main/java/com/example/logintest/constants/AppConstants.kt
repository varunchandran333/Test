package com.example.logintest.constants

import android.location.Location

class AppConstants {
    companion object {
        internal const val SAVED_USERNAME = "SAVED_USERNAME"
        internal const val SAVED_PASSWORD = "SAVED_PASSWORD"
        internal const val PASSED_DATA = "PASSED_DATA"
        internal const val ID = "ID"
    }
    var globalUserCurrentLocation: Location? = null
}