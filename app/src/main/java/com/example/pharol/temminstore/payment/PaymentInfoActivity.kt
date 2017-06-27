package com.example.pharol.temminstore.payment

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.pharol.temminstore.R
import com.example.pharol.temminstore.transaction.TransactionActivity
import com.example.pharol.temminstore.util.toMoneyString
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.example.pharol.temminstore.TemminApplication
import com.example.pharol.temminstore.di.ui.ActivityModule
import com.example.pharol.temminstore.di.ui.DaggerActivityComponent
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject


class PaymentInfoActivity : AppCompatActivity(), LifecycleRegistryOwner {
    var mRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = this.mRegistry

    val component      by  lazy { DaggerActivityComponent.builder().activityModule(ActivityModule(this)).
            applicationComponent((application as TemminApplication).applicationComponent).build() }

    @Inject lateinit var paymentInfoViewFactory: PaymentInfoViewModelFactory
    lateinit var paymentViewModel: PaymentInfoViewModel

    val et_card_name   by  lazy { findViewById(R.id.et_card_name) as EditText }
    val et_card_number by  lazy { findViewById(R.id.et_card_number) as EditText }
    val et_card_cvv    by  lazy { findViewById(R.id.et_card_cvv) as EditText }
    val et_card_date   by  lazy { findViewById(R.id.et_card_date) as EditText }
    val tv_total_price by  lazy { findViewById(R.id.tv_total_price) as TextView }
    val bt_confirm     by  lazy { findViewById(R.id.bt_confirm) as Button }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_payment_info)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupMasks()

         paymentViewModel = ViewModelProviders.of(this,paymentInfoViewFactory).get(PaymentInfoViewModel::class.java)
         paymentViewModel.getTotalCart().observe(this, Observer<BigDecimal> {
             tv_total_price.text = it?.toMoneyString()
             val moneyString = resources.getString(R.string.buy) +" "+  it?.toMoneyString()
             bt_confirm.text = moneyString
             paymentViewModel.value = it ?: BigDecimal.ZERO
         })

        bt_confirm.setOnClickListener({
            if ( hasValidFields()){
                paymentViewModel.pay(et_card_number.text.toString(), et_card_cvv.text.toString(),
                        et_card_name.text.toString(), Calendar.getInstance().time)
                startActivity(Intent(this, TransactionActivity::class.java))
                finish()
            }
        })

    }


    private fun  hasValidFields(): Boolean {
        if (et_card_name.text.isEmpty()) {
            et_card_name.error = "Card Holder Name is Empty! Please fill in name"
            et_card_name.requestFocus()
            return false
        }
        if (et_card_number.text.length != 19) {
            et_card_number.error = "Card Number invalid. Should have 16 digits"
            et_card_number.requestFocus()
            return false
        }
        if (et_card_cvv.text.length != 3) {
            et_card_cvv.error = "Card CVV invalid. Should have 3 digits"
            et_card_cvv.requestFocus()
            return false
        }
        if (et_card_date.text.length != 5){
            et_card_date.error = "Card Expiration date invalid. Should be MM/YY"
            et_card_date.requestFocus()
            return false
        } else {
            val month = et_card_date.text.toString().split("/")[0].toInt()
            if ( month > 12 || month == 0 ){
                et_card_date.error = "Card Expiration date invalid. Month should be 1 to 12"
                et_card_date.requestFocus()
                return false
            }
        }
        return true
    }

    private fun setupMasks() {
        et_card_number.addTextChangedListener(MaskTextWatcher(et_card_number, SimpleMaskFormatter("NNNN NNNN NNNN NNNN")))
        et_card_cvv.addTextChangedListener(MaskTextWatcher(et_card_cvv, SimpleMaskFormatter("NNN")))
        et_card_date.addTextChangedListener(MaskTextWatcher(et_card_date, SimpleMaskFormatter("NN/NNNN")))
        et_card_name.filters = arrayOf<InputFilter>().plus( InputFilter { charSequence: CharSequence, _, _, _, _, _->
            if(charSequence == ""){
                charSequence
            } else if(charSequence.toString().matches(Regex("[a-zA-Z ]+"))){
                charSequence
            } else
                charSequence.subSequence(0,charSequence.length-1)
        } )

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
