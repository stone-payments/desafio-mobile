package com.stone.starwarsstore.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stone.starwarsstore.R
import com.stone.starwarsstore.extension.asDateString
import com.stone.starwarsstore.extension.formatPrice
import com.stone.starwarsstore.model.Order
import kotlinx.android.synthetic.main.row_order.view.*

class OrdersAdapter (val mOrders: MutableList<Order>) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.row_order, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = mOrders[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return mOrders.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderPrice = view.rowOrder_tvTotalPrice
        val orderTime = view.rowOrder_tvOrderTime
        val orderCardHolder = view.rowOrder_tvCardHolder
        val orderLastFourDigits = view.rowOrder_tvCardLastDigits

        fun bind(order: Order?) {
            orderPrice.text = order?.value?.formatPrice()
            orderCardHolder.text = order?.cardHolderName
            orderLastFourDigits.text = order?.lastFourDigits
            orderTime.text = order?.createdAt?.asDateString()
        }
    }

}