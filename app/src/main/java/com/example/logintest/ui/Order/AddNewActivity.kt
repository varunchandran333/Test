package com.example.logintest.ui.Order

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.logintest.R
import com.example.logintest.constants.AppConstants.Companion.PASSED_DATA
import com.example.logintest.data.model.order.Orders
import com.example.logintest.databinding.AddNewOrderLayoutBinding
import com.example.logintest.events.EventListeners
import com.example.logintest.ui.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class AddNewActivity : AppCompatActivity() {
    private var order: Orders? = null
    private val viewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory())
                .get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lBinder: AddNewOrderLayoutBinding = DataBindingUtil.setContentView(
                this,
                R.layout.add_new_order_layout
        )
        order = intent.getParcelableExtra(PASSED_DATA)
        if (order != null)
            lBinder.order = order
        lBinder.eventListener = object : EventListeners.OrderEvents {
            override fun onPurchaseDate() {
                val cal = Calendar.getInstance()
                val dateSetListener = DatePickerDialog
                        .OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                            cal.set(Calendar.YEAR, year)
                            cal.set(Calendar.MONTH, monthOfYear)
                            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            lBinder.textViewDate.text = cal.time.toString("dd/MM/YYYY")
                        }

                val mDatePicker = DatePickerDialog(this@AddNewActivity, dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH))
//                mDatePicker.datePicker.maxDate = System.currentTimeMillis()
                mDatePicker.show()
            }

            override fun onSubmit() {
                if (order != null) {
                    order = Orders(order!!.orderNo,
                            lBinder.textViewDate.text.toString(),
                            lBinder.editTextName.text.toString(),
                            lBinder.editTextAddress.text.toString(),
                            lBinder.editTextContact.text.toString(),
                            lBinder.editTextCount.text.toString(), "")
                    viewModel.update(order!!)
                } else {
                    order = Orders(0,
                            lBinder.textViewDate.text.toString(),
                            lBinder.editTextName.text.toString(),
                            lBinder.editTextAddress.text.toString(),
                            lBinder.editTextContact.text.toString(),
                            lBinder.editTextCount.text.toString(), "")
                    viewModel.insert(order!!)
                }
                finish()
            }
        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}

