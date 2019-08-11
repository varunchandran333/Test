package com.example.logintest.data.model

/**
 * Data class that captures user state for logged in users data
 */
data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)