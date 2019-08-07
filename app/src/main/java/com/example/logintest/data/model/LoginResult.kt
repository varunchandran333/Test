package com.example.logintest.data.model

data class LoginResult(
        val success: LoggedInUser? = null,
        val error: Int? = null
)