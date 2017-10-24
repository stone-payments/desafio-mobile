package com.stone.starwarsstore.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.stone.starwarsstore.Cart
import com.stone.starwarsstore.R
import com.stone.starwarsstore.`interface`.OnProductAddedListener
import com.stone.starwarsstore.model.Product
import com.stone.starwarsstore.service.RestService
import com.stone.starwarsstore.ui.adapter.ProductsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity(), OnProductAddedListener {

    val RC_PRODUCT_ADDED: Int = 55

    var productsList = mutableListOf<Product>()
    lateinit var productsAdapter : ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productsAdapter = ProductsAdapter(productsList, this) { product ->
            val intent = ProductDetailActivity.newIntent(this, product)
            startActivityForResult(intent, RC_PRODUCT_ADDED)
        }

        setUpRecyclerView()

        actMain_fbCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        getProductsFromService()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
        when (requestCode) {
            RC_PRODUCT_ADDED -> showMessage(data?.extras?.getSerializable("product") as Product?)
        }
    }

    fun showMessage(product: Product?) {
        val coordinatorLayout = actMain_coordinatorLayout
        Snackbar.make(coordinatorLayout, getString(R.string.snackbar_added, product?.title), Snackbar.LENGTH_LONG).setAction(getString(R.string.str_see), {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }).show()
    }

    fun setUpRecyclerView(){
        actMain_recyclerView.adapter = productsAdapter
        actMain_recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        actMain_recyclerView.layoutManager = layoutManager
    }

    fun getProductsFromService() {
        RestService().loadProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ product ->
                    productsList.add(product)
                }, { e ->
                    e.printStackTrace()
                }, {
                    actMain_progressBar.visibility = View.GONE
                    actMain_recyclerView.visibility = View.VISIBLE
                    productsAdapter.notifyDataSetChanged()
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_orders, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_orders -> navigateToOrders()
        }
        return super.onOptionsItemSelected(item)
    }

    fun navigateToOrders(): Boolean {
        val mIntent = Intent(this, OrdersActivity::class.java)
        this.startActivity(mIntent)
        return true
    }

    override fun onProductAddedToCart(product: Product?) {
        Cart.addProduct(product)
        showMessage(product)
    }
}
