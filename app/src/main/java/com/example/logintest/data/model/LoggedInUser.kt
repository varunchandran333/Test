package com.example.logintest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Parcelize
data class LoggedInUser(
    val userId: String,
    val displayName: String,
    val status: Boolean
) : Parcelable
