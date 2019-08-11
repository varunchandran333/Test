package com.example.logintest.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.logintest.R
import com.example.logintest.data.model.LoginFormState
import com.example.logintest.data.model.LoginResult
import com.example.logintest.data.repository.LoginDataSource

open class LoginViewModel(val dataSource: LoginDataSource) : ViewModel() {
    private val loginData = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = loginData
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        val result = dataSource.login(username, password)
        if (result.status) {
            _loginResult.value = LoginResult(success = result)
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }
//    fun onRemember(username: String, password: String) {
//
//    }
    fun loginDataChanged(username: String, password: String) {
        if (isUserNameValid(username)) {
            loginData.value = LoginFormState(usernameError = null)
        }
        if (!isUserNameValid(username)) {
            loginData.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            loginData.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            loginData.value = LoginFormState(isDataValid = true)
        }
    }

    // A username validation check
    fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A password validation check
    fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}