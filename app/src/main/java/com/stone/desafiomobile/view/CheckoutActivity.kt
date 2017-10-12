package com.stone.desafiomobile.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.stone.desafiomobile.R
import com.stone.desafiomobile.model.Product

class CheckoutActivity : AppCompatActivity() {
    lateinit internal var mCartItens: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mCartItens = intent.getSerializableExtra(ARG_CART) as ArrayList<Product>
    }

    companion object {
        val ARG_CART = "arg_cart"
    }

}
