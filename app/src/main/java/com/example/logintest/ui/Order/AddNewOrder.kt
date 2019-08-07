package com.example.logintest.ui.Order

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.logintest.R
import com.example.logintest.data.model.order.Orders
import com.example.logintest.databinding.AddNewOrderLayoutBinding
import com.example.logintest.events.EventListeners
import kotlinx.android.synthetic.main.add_new_order_layout.*
import java.text.SimpleDateFormat
import java.util.*


private const val ARG_ORDER = "order"

/**
 * A simple [Fragment] subclass.
 * Use the [AddNewOrder.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddNewOrder : Fragment() {
    private var order: Orders? = null
    private lateinit var lBinder: AddNewOrderLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getParcelable(ARG_ORDER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        lBinder = DataBindingUtil.inflate(inflater, R.layout.add_new_order_layout,
                container, false)
        return lBinder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                            lBinder.textViewDate.text=cal.time.toString("dd/MM/YYYY")
                        }

                val mDatePicker = DatePickerDialog(context, dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH))
//                mDatePicker.datePicker.maxDate = System.currentTimeMillis()
                mDatePicker.show()
            }

            override fun onSubmit() {
                if(order!=null){
                    order = Orders(order!!.orderNo,
                            lBinder.textViewDate.text.toString(),
                            lBinder.editTextName.text.toString(),
                            lBinder.editTextAddress.text.toString(),
                            lBinder.editTextContact.text.toString(),
                            lBinder.editTextCount.text.toString(), "")
                    OrderActivity.getviewmodel((activity as OrderActivity)).update(order!!)
                }
                else{
                    order = Orders(0,
                            lBinder.textViewDate.text.toString(),
                            lBinder.editTextName.text.toString(),
                            lBinder.editTextAddress.text.toString(),
                            lBinder.editTextContact.text.toString(),
                            lBinder.editTextCount.text.toString(), "")
                    OrderActivity.getviewmodel((activity as OrderActivity)).insert(order!!)
                }

                activity?.onBackPressed()
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddNewOrder.
         */
        @JvmStatic
        fun newInstance(order: Orders) =
                AddNewOrder().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_ORDER, order)
                    }
                }
    }
}
