package com.example.logintest.ui.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.logintest.data.model.order.Orders
import com.example.logintest.data.repository.OrderRepository
import com.example.logintest.database.OrderDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: OrderRepository
    val allOrders: LiveData<List<Orders>>
    init {
        val orderDao = OrderDatabase.getDatabase(viewModelScope).orderDao()
        repository = OrderRepository(orderDao)
        allOrders = repository.allOrders

    }

    fun delete(orderNo: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(orderNo)
    }

}