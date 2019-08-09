package com.example.logintest.ui.Order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.logintest.data.model.order.Orders
import com.example.logintest.data.repository.OrderRepository
import com.example.logintest.database.OrderDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) :AndroidViewModel(application){
    private val repository: OrderRepository
    val allOrders: LiveData<List<Orders>>

    init {
        val wordsDao = OrderDatabase.getDatabase(viewModelScope).orderDao()
        repository = OrderRepository(wordsDao)
        allOrders = repository.allOrders
    }
    fun insert(order: Orders) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(order)
    }
    fun delete(orderNo: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(orderNo)
    }
    fun update(order: Orders) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(order)
    }

}