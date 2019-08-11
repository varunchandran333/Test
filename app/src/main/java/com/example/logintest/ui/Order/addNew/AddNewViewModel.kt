package com.example.logintest.ui.Order.addNew

import android.Manifest.permission
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.logintest.BaseApplication
import com.example.logintest.constants.AppConstants.Companion.MY_PERMISSIONS_REQUEST_LOCATION
import com.example.logintest.data.model.order.Orders
import com.example.logintest.data.repository.OrderRepository
import com.example.logintest.data.repository.ReverseGeoCodingRepo
import com.example.logintest.database.OrderDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddNewViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: OrderRepository
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var reverseGeoCodingRepo = ReverseGeoCodingRepo()

    init {
        val orderDao = OrderDatabase.getDatabase(viewModelScope).orderDao()
        repository = OrderRepository(orderDao)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(BaseApplication.getContext())
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

    fun reverseGeoCoding(latitude: Double?, longitude: Double?): MutableLiveData<String> {
        return reverseGeoCodingRepo.reverseGeoCoding(latitude, longitude)
    }

    fun getCurrentLocationNew(activity: AddNewActivity): MutableLiveData<Location> {
        val mediatorLiveData = MutableLiveData<Location>()
        try {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    activity,
                    permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val mLocationRequest = LocationRequest.create()
                mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                fusedLocationClient?.lastLocation?.addOnSuccessListener {
                    // null check
                    if (it != null) {
                        mediatorLiveData.value = it
                    }
                }
                fusedLocationClient?.lastLocation?.addOnFailureListener {
                    // null check
                    if (it != null) {

                    }
                }

            } else {
                val permissionArray = arrayOf(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(activity, permissionArray, MY_PERMISSIONS_REQUEST_LOCATION)
                }

            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return mediatorLiveData
    }
}