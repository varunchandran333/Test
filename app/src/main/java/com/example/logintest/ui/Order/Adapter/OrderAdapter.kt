package com.example.logintest.ui.Order.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.logintest.R
import com.example.logintest.data.model.order.Orders
import com.example.logintest.databinding.RecyclerviewItemBinding
import com.example.logintest.events.EventListeners
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class OrderAdapter internal constructor(orders: List<Orders>, context: Context, listener: EventListeners.AdapterEvents) :
        androidx.recyclerview.widget.RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var listner: EventListeners.AdapterEvents = listener
    private var mRecentlyDeletedItemPosition: Int = 0
    private lateinit var mRecentlyDeletedItem: Orders
    private var order: List<Orders> = orders

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView: RecyclerviewItemBinding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.context), R.layout.recyclerview_item, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun getItemCount() = order.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(order[position])
    }

    inner class OrderViewHolder(itemView: RecyclerviewItemBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView.root) {
        fun bind(item: Orders) {
            itemView.setOnLongClickListener {
                listner.onLongClick(order[layoutPosition])
                return@setOnLongClickListener true
            }
            itemView.textViewCustomerName.text = item.customerName
            itemView.textViewCustomerAddress.text = item.customerAddress
            itemView.textViewTotalOrders.text = item.noOfOrders
        }
    }

    fun deleteItem(position: Int) {
        mRecentlyDeletedItem = order[position]
        mRecentlyDeletedItemPosition = position
        listner.onDelete(mRecentlyDeletedItem, mRecentlyDeletedItemPosition)
        notifyItemRemoved(position)
    }
}