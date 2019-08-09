package com.example.logintest.events

import com.example.logintest.data.model.order.Orders

interface EventListeners{
    interface LoginEvents {
        fun onLogin()
        fun onRemember()
    }
    interface LogoutEvents {
        fun onLogout()
    }
    interface OrderEvents {
        fun onPurchaseDate()
        fun onSubmit()
    }
    interface AdapterEvents{
        fun onDelete(orderNo: Long, id: String)
        fun onLongClick(order: Orders, id: String)
    }
}