package com.example.logintest.ui.Order

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.logintest.R
import com.example.logintest.SwipeToDeleteCallback
import com.example.logintest.constants.AppConstants.Companion.PASSED_DATA
import com.example.logintest.data.model.order.Orders
import com.example.logintest.events.EventListeners
import com.example.logintest.ui.Order.Adapter.OrderAdapter
import com.example.logintest.ui.ViewModelFactory
import kotlinx.android.synthetic.main.activity_listorder.*


class OrderActivity : AppCompatActivity(), EventListeners.AdapterEvents {
    override fun onLongClick(order: Orders) {
        val intent = Intent(this, AddNewActivity::class.java)
        intent.putExtra(PASSED_DATA, order)
        startActivity(intent)
        //replaceFragment(AddNewOrder.newInstance(order), R.id.container)
    }

    override fun onDelete(order: Orders) {
        viewModel.delete(orderNo = order.orderNo)
    }

    var adapter: OrderAdapter? = null

    private val viewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory())
                .get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listorder)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            val intent = Intent(this, AddNewActivity::class.java)
            startActivity(intent)
            //addFragment(AddNewOrder(), R.id.container)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.allOrders.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                setRecyclerView(it)
            }
        })

    }

    private fun setRecyclerView(order: List<Orders>) {
        adapter = OrderAdapter(order, this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter, this))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    companion object {
        fun getviewmodel(orderActivity: OrderActivity): OrderViewModel {
            return orderActivity.viewModel
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()

        } else {
            supportFragmentManager.popBackStack()
        }

    }

}
