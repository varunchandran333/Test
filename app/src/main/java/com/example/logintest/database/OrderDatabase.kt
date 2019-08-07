package com.example.logintest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.logintest.BaseApplication
import com.example.logintest.data.model.order.Orders
import com.example.logintest.data.repository.OrderDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Orders::class), version = 1)
public abstract class OrderDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDAO

    companion object {
        @Volatile
        private var INSTANCE: OrderDatabase? = null

        fun getDatabase(scope: CoroutineScope): OrderDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        BaseApplication.getContext(),
                        OrderDatabase::class.java,
                        "Word_database"
                ).addCallback(OrderDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class OrderDatabaseCallback(
            private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    //populateDatabase(database.orderDao())
                }
            }
        }

        suspend fun populateDatabase(orderDAO: OrderDAO) {
            orderDAO.deleteAll()
            var order = Orders(1,"1","dfsdf","sdsd","2312312","5","123212ada")
            orderDAO.insert(order)
            order = Orders(2,"1","dfsdf","sdsd","2312312","6","123212ada")
            orderDAO.insert(order)
        }
    }
}