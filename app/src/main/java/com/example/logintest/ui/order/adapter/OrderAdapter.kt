package com.example.logintest.ui.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.logintest.R
import com.example.logintest.data.model.order.Orders
import com.example.logintest.databinding.RecyclerviewItemBinding
import com.example.logintest.events.EventListeners
import com.example.logintest.ui.order.Adapterimport.FirestoreAdapter
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class OrderAdapter internal constructor(orders: List<Orders>, listener: EventListeners.AdapterEvents, query: Query) :
    FirestoreAdapter<OrderAdapter.OrderViewHolder>(query) {

    private var listner: EventListeners.AdapterEvents = listener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView: RecyclerviewItemBinding = DataBindingUtil.inflate(
            LayoutInflater
                .from(parent.context), R.layout.recyclerview_item, parent, false
        )
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getSnapshot(position))
    }

    inner class OrderViewHolder(itemView: RecyclerviewItemBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView.root) {
        fun bind(orders: DocumentSnapshot) {
            val item = orders.toObject(Orders::class.java)
            itemView.setOnLongClickListener {
                val id = getSnapshot(layoutPosition).id
                val order = getSnapshot(layoutPosition).toObject(Orders::class.java)
                if (order != null)
                    listner.onLongClick(order, id)
                return@setOnLongClickListener true
            }
            itemView.textViewCustomerName.text = item?.customerName
            itemView.textViewCustomerAddress.text = item?.customerAddress
            itemView.textViewTotalOrders.text = item?.noOfOrders
            if (item?.location.isNullOrEmpty())
                itemView.textViewLocationLabel.visibility = View.GONE
            else {
                itemView.textViewLocationLabel.visibility = View.VISIBLE
                itemView.textViewLocationLabel.text = item?.location
            }

        }
    }

    fun deleteItem(position: Int) {
        val orderNo = getSnapshot(position).getLong("orderNo")
        val id = getSnapshot(position).id
        if (orderNo != null)
            listner.onDelete(orderNo = orderNo, id = id)
        notifyItemRemoved(position)
    }
}