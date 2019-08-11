package com.example.logintest.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.logintest.data.repository.LoginDataSource
import com.example.logintest.ui.Login.LoginViewModel
import com.example.logintest.ui.Order.OrderViewModel
import com.example.logintest.ui.Order.addNew.AddNewViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                    dataSource = LoginDataSource()
            ) as T
        }
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(
                    application = Application()
            ) as T
        }
        if (modelClass.isAssignableFrom(AddNewViewModel::class.java)) {
            return AddNewViewModel(
                application = Application()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
