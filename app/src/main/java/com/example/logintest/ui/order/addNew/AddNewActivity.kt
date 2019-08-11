package com.example.logintest.ui.order.addNew

import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.logintest.R
import com.example.logintest.constants.AppConstants.Companion.MY_PERMISSIONS_REQUEST_LOCATION
import com.example.logintest.constants.AppConstants.Companion.ORDER_ID
import com.example.logintest.constants.AppConstants.Companion.PASSED_DATA
import com.example.logintest.data.model.order.Orders
import com.example.logintest.databinding.AddNewOrderLayoutBinding
import com.example.logintest.events.EventListeners
import com.example.logintest.ui.ViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class AddNewActivity : AppCompatActivity() {
    private var order: Orders? = null
    private var idtoUpdate: String? = null
    lateinit var db: FirebaseFirestore
    var location: String? = null
    private val viewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory())
            .get(AddNewViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lBinder: AddNewOrderLayoutBinding = DataBindingUtil.setContentView(
            this,
            R.layout.add_new_order_layout
        )
        order = intent.getParcelableExtra(PASSED_DATA)
        idtoUpdate = intent.getStringExtra(ORDER_ID)
        db = FirebaseFirestore.getInstance()
        if (order != null)
            lBinder.order = order
        lBinder.eventListener = object : EventListeners.OrderEvents {
            override fun getLocation() {
                viewModel.getCurrentLocationNew(this@AddNewActivity)
                    .observe(this@AddNewActivity, androidx.lifecycle.Observer {
                        if (it != null) {
                            viewModel.reverseGeoCoding(it.latitude, it.longitude)
                                .observe(this@AddNewActivity, androidx.lifecycle.Observer { locationString ->
                                    if (locationString != null) {
                                        lBinder.textViewLocation.text = locationString
                                    }
                                })
                        }
                    })
            }

            override fun onPurchaseDate() {
                val cal = Calendar.getInstance()
                val dateSetListener = DatePickerDialog
                    .OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        lBinder.textViewDate.text = cal.time.toString("dd/MM/yyyy")
                    }

                val mDatePicker = DatePickerDialog(
                    this@AddNewActivity, dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )
//                mDatePicker.datePicker.maxDate = System.currentTimeMillis()
                mDatePicker.show()
            }

            override fun onSubmit() {
                val orderCollection = db.collection("Orders")
                if (order != null && idtoUpdate != null) {
                    order = Orders(
                        order!!.orderNo,
                        lBinder.textViewDate.text.toString(),
                        lBinder.editTextName.text.toString(),
                        lBinder.editTextAddress.text.toString(),
                        lBinder.editTextContact.text.toString(),
                        lBinder.editTextCount.text.toString(), lBinder.textViewLocation.text.toString()
                    )
                    db.collection("Orders")
                        .document(idtoUpdate!!)
                        .update(
                            mapOf(
                                "orderNo" to order!!.orderNo,
                                "orderDueDate" to order!!.orderDueDate,
                                "customerName" to order!!.customerName,
                                "customerAddress" to order!!.customerAddress,
                                "customerPhone" to order!!.customerPhone,
                                "noOfOrders" to order!!.noOfOrders,
                                "location" to order!!.location
                            )
                        )
                        .addOnSuccessListener {
                            viewModel.update(order!!)
                        }
                } else {
                    order = Orders(
                        0,
                        lBinder.textViewDate.text.toString(),
                        lBinder.editTextName.text.toString(),
                        lBinder.editTextAddress.text.toString(),
                        lBinder.editTextContact.text.toString(),
                        lBinder.editTextCount.text.toString(), lBinder.textViewLocation.text.toString()
                    )
                    viewModel.insert(order!!)
                    orderCollection.add(order!!).addOnFailureListener {
                        viewModel.delete(order!!.orderNo)
                    }
                }
                finish()
            }
        }
    }

    /**
     * method invoked on Request Permissions Result
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    viewModel.getCurrentLocationNew(this)

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}

