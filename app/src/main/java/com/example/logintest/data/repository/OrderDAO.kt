package com.example.logintest.data.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.logintest.data.model.order.Orders


@Dao
interface OrderDAO {
    @Query("SELECT * from order_table ORDER BY orderNo ASC")
    fun getAllOrders(): LiveData<List<Orders>>

    @Insert
    suspend fun insert(order: Orders)

    @Query("DELETE FROM order_table")
    fun deleteAll()

    @Query("DELETE FROM order_table WHERE orderNo = :orderNo")
    fun deleteByUserId(orderNo: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(order: Orders)
}