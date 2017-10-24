package com.stone.starwarsstore.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.stone.starwarsstore.R
import com.stone.starwarsstore.model.Order
import com.stone.starwarsstore.ui.adapter.OrdersAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_orders.*
import kotlin.properties.Delegates


class OrdersActivity : AppCompatActivity() {

    private var realm: Realm by Delegates.notNull()
    lateinit var ordersAdapter: OrdersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        setTitle(getString(R.string.title_orders))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()

        realm = Realm.getDefaultInstance()

        val orders = realm.where(Order::class.java).findAll()

        if (orders.isEmpty()) {
            actOrders_recyclerView.visibility = View.GONE
            actOrder_tvEmpty.visibility = View.VISIBLE
        }

        ordersAdapter = OrdersAdapter(orders)
        actOrders_recyclerView.adapter = ordersAdapter
        ordersAdapter.notifyDataSetChanged()
    }

    fun setupRecyclerView(){
        actOrders_recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        actOrders_recyclerView.layoutManager = layoutManager
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}