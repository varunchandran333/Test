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
import com.example.logintest.constants.AppConstants.Companion.ORDER_ID
import com.example.logintest.constants.AppConstants.Companion.PASSED_DATA
import com.example.logintest.data.model.order.Orders
import com.example.logintest.events.EventListeners
import com.example.logintest.ui.Order.Adapter.OrderAdapter
import com.example.logintest.ui.Order.addNew.AddNewActivity
import com.example.logintest.ui.ViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_listorder.*


class OrderActivity : AppCompatActivity(), EventListeners.AdapterEvents {
    lateinit var db: FirebaseFirestore
    private var mQuery: Query? = null
    var adapter: OrderAdapter? = null
    override fun onStart() {
        super.onStart()
        if (adapter != null) {
            adapter?.startListening()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (adapter != null) {
            adapter?.stopListening()
        }
    }

    override fun onLongClick(order: Orders, id: String) {
        val intent = Intent(this, AddNewActivity::class.java)
        intent.putExtra(ORDER_ID, id)
        intent.putExtra(PASSED_DATA, order)
        startActivity(intent)
        //replaceFragment(AddNewOrder.newInstance(order), R.id.container)
    }

    override fun onDelete(orderNo: Long, id: String) {
        db.collection("Orders").document(id)
                .delete()
                .addOnSuccessListener { viewModel.delete(orderNo = orderNo.toInt()) }
    }


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
        db = FirebaseFirestore.getInstance()
        mQuery = db.collection("Orders")
                .orderBy("orderNo", Query.Direction.ASCENDING)
                .limit(10)
        if (mQuery != null)
            setRecyclerView(mQuery!!)
        else
        viewModel.allOrders.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                //setRecyclerView(it)
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setRecyclerView(mQuery: Query) {
        adapter = OrderAdapter(emptyList(), this, mQuery)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter, this))
        itemTouchHelper.attachToRecyclerView(recyclerView)
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
