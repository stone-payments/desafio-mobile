package com.stone.starwarsstore.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.stone.starwarsstore.Cart
import com.stone.starwarsstore.R
import com.stone.starwarsstore.`interface`.OnProductAmountChangedListener
import com.stone.starwarsstore.extension.formatPrice
import com.stone.starwarsstore.ui.adapter.CartProductsAdapter
import kotlinx.android.synthetic.main.activity_cart.*
import org.jetbrains.anko.toast

class CartActivity : AppCompatActivity(), OnProductAmountChangedListener {

    val REQUEST_CODE_FINISH_PURCHASE: Int = 2
    var cartProductsAdapter: CartProductsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setTitle(getString(R.string.title_cart))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cartProductsAdapter = CartProductsAdapter(Cart.getCartProducts(), this)

        setupRecyclerView()

        actProdCart_tvTotalPrice.text = Cart.getTotalPrice()?.formatPrice()

        actCart_btNext.setOnClickListener {
            if (!Cart.isCartEmpty()) {
                val intent = Intent(this, FinishOrderActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_FINISH_PURCHASE)
            } else {
                toast(getString(R.string.toast_add_cart_items))
            }
        }
    }

    fun setupRecyclerView() {
        val recyclerView = actCart_listProducts
        recyclerView.adapter = cartProductsAdapter
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                REQUEST_CODE_FINISH_PURCHASE -> finish()
            }
    }

    override fun onProductAmountChanged() {
        actProdCart_tvTotalPrice.text = Cart.getTotalPrice()?.formatPrice()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}