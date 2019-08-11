package com.example.logintest.data.repository

import com.example.logintest.data.model.LoggedInUser
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): LoggedInUser {
        //User details can be save to local storage or pass to server
        return try {
            LoggedInUser(
                userId = UUID.randomUUID().toString(),
                displayName = "Sachin Tendulkar", status = true
            )
        } catch (e: Exception) {
            LoggedInUser(
                userId = "0",
                displayName = "", status = false
            )
        }
    }
//    fun onRemember(username: String, password: String): Boolean {
//        val userDetails = UserDetails(username = username, password = password)
//        return true
//    }
}

