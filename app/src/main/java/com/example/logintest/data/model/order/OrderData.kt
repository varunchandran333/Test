package com.example.logintest.data.model.order

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderData(  var orderDueDate: String="",
                     var customerName: String="",
                     var customerAddress: String="",
                     var customerPhone: String="",
                     var noOfOrders: String="",
                     var location: String=""):Parcelable