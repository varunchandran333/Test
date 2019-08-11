package com.example.logintest.data.model

/**
 * Data class that captures user logged in status
 */
data class LoginResult(
    val success: LoggedInUser? = null,
    val error: Int? = null
)