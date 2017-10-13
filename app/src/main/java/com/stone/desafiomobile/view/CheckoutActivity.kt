package com.stone.desafiomobile.view

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.stone.desafiomobile.R
import com.stone.desafiomobile.di.DaggerInjectionComponent
import com.stone.desafiomobile.di.DatabaseModule
import com.stone.desafiomobile.di.RetrofitModule
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.model.Purchase
import com.stone.desafiomobile.utils.formatPriceReal
import com.stone.desafiomobile.viewmodel.CheckoutVm

class CheckoutActivity : BaseActivity() {
    companion object {
        val ARG_CART = "arg_cart"
    }

    lateinit internal var mCartItens: ArrayList<Product>

    lateinit internal var mCardNumberET: EditText
    lateinit internal var mHolderNameET: EditText
    lateinit internal var mExpDateET: EditText
    lateinit internal var mCvvCodeET: EditText
    lateinit internal var mValueView: TextView
    lateinit internal var mBuyButton: Button

    lateinit internal var mViewModel: CheckoutVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mCartItens = intent.getSerializableExtra(ARG_CART) as ArrayList<Product>

        setWidgets()

        mViewModel = ViewModelProviders.of(this).get(CheckoutVm::class.java)


        val injectionComponent = DaggerInjectionComponent.builder()
                .retrofitModule(RetrofitModule())
                .databaseModule(DatabaseModule(this))
                .build()
        injectionComponent.inject(mViewModel)

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

        mViewModel.buyProducts(purchase, { result ->
            showAlert(result)
        })

    }

    fun showAlert(message: Int) {
        val dialog = AlertDialog.Builder(this)
                .setMessage(getString(message))
                .setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }).create()
        dialog.show()
    }


}
