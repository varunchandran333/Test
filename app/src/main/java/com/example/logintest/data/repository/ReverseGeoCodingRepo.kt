package com.example.logintest.data.repository

import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.logintest.myApp.BaseApplication
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ReverseGeoCodingRepo {

    fun reverseGeoCoding(latitude: Double?, longtitude: Double?): MutableLiveData<String> {
        val mutableLiveData = MediatorLiveData<String>()
        if (latitude == null || longtitude == null) {
            mutableLiveData.value = ""
            return mutableLiveData
        }
        val geocoder = Geocoder(BaseApplication.getContext(), Locale.getDefault())
        var errorMessage = ""
        var mAddress = ""

        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(
                latitude, longtitude,
                // In this sample, get just a single address.
                1
            )
            addresses.forEach {
                mAddress = it.getAddressLine(0)
            }
//            for (i in addresses!!.indices) {
//                mAddress = mAddress + (addresses[i].featureName
//                        + ", " + addresses[i].thoroughfare
//                        + ", " + addresses[i].subLocality
//                        + ", " + addresses[i].subAdminArea)
//            }
            mutableLiveData.value = mAddress
        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            errorMessage = "Network error"
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "Invalide Coordinate used"
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No address Foound"
            }
        } else {
            val address = addresses[0]
            val addressFragments = ArrayList<String>()

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for (i in 0..address.maxAddressLineIndex) {
                addressFragments.add(address.getAddressLine(i))
            }
            errorMessage = "No Address Found"

        }
        return mutableLiveData
    }

}