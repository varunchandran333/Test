package com.example.logintest.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.logintest.data.model.order.Orders

class OrderRepository(private val orderDAO: OrderDAO) {
    val allOrders: LiveData<List<Orders>> = orderDAO.getAllOrders()
    @WorkerThread
    suspend fun insert(orders: Orders) {
        orderDAO.insert(orders)
    }

    @WorkerThread
    fun delete(order: Int) {
        orderDAO.deleteByUserId(order)
    }
    @WorkerThread
    fun update(order: Orders) {
        orderDAO.update(order)
    }
}