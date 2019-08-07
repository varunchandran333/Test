package com.example.logintest.data.model.order

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "order_table")
data class Orders(@PrimaryKey(autoGenerate = true) val orderNo: Int,
                  val orderDueDate: String,
                  val customerName: String,
                  val customerAddress: String,
                  val customerPhone: String,
                  val noOfOrders: String,
                  val location: String):Parcelable