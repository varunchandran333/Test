package com.example.logintest

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.logintest.data.model.order.Orders
import com.example.logintest.database.OrderDatabase
import com.google.firebase.firestore.FirebaseFirestore



val localDB by lazy {
    val context = BaseApplication.getContext()
    Room.databaseBuilder(context,
            OrderDatabase::class.java, "order_table").fallbackToDestructiveMigration().build()
}
val mFirestore by lazy {
    FirebaseFirestore.getInstance()
}

@Database(entities = [Orders::class], version = 1)

abstract class OrderDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDAO
}

@Dao
interface OrderDAO {
    @Query("SELECT * from order_table ORDER BY orderNo ASC")
    fun getAllOrders(): LiveData<List<Orders>>

    @Insert
    suspend fun insert(order: Orders)

    @Query("DELETE FROM order_table")
    fun deleteAll()
}
////TODO call in on create of app
//fun updateGlobalUserFromDB() {
//    object : Thread() {
//        override fun run() {
//            globalUser = localDB.userDAO().getProfile()
//        }
//    }.start()
//}


////TODO call on logout
//fun clearUserDetailsFromLocalDB() {
//    object : Thread() {
//        override fun run() {
//            localDB.userDAO().deleteAllUsers()
//            //localDB.recentLocationDAO().deleteAllLocations()
//        }
//    }.start()
//}
//
//
////TODO call on login,sign up, get profile, update profile, update profile settings...
//fun saveUserDetailsToDB(user: ProfileDetail) {
//    object : Thread() {
//        override fun run() {
//            localDB.userDAO().save(user)
//            globalUser = user
//        }
//    }.start()
//}

