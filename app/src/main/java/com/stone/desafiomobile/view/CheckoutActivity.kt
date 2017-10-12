package com.stone.desafiomobile.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.stone.desafiomobile.R
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.model.Purchase
import com.stone.desafiomobile.utils.formatPriceReal

class CheckoutActivity : AppCompatActivity() {
    lateinit internal var mCartItens: ArrayList<Product>

    lateinit internal var mCardNumberET: EditText
    lateinit internal var mHolderNameET: EditText
    lateinit internal var mExpDateET: EditText
    lateinit internal var mCvvCodeET: EditText
    lateinit internal var mValueView: TextView
    lateinit internal var mBuyButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mCartItens = intent.getSerializableExtra(ARG_CART) as ArrayList<Product>

        setWidgets()

    }

    fun setWidgets() {
        mCardNumberET = findViewById(R.id.card_number)
        mHolderNameET = findViewById(R.id.card_holder_name)
        mExpDateET = findViewById(R.id.exp_date)
        mCvvCodeET = findViewById(R.id.cvv)
        mValueView = findViewById(R.id.purchase_value)
        mValueView.text = defineValue(mCartItens).formatPriceReal()

        mBuyButton = findViewById(R.id.buy_button)
        mBuyButton.setOnClickListener { completePurchase() }
    }

    companion object {
        val ARG_CART = "arg_cart"
    }


    fun defineValue(cartItens: List<Product>): Long {
        var value = 0L
        cartItens.map { product -> value += product.price ?: 0 }
        return value
    }

    fun completePurchase() {
        val purchase = Purchase(
                mCardNumberET.text.toString(),
                defineValue(mCartItens),
                mCvvCodeET.text.toString().toLong(),
                mHolderNameET.text.toString(),
                mExpDateET.text.toString()
        )

        Log.d(this::class.simpleName, purchase.toString())
    }


}
