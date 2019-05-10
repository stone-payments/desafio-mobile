package br.com.stone.vianna.starstore.view.card

import android.os.Bundle
import br.com.stone.vianna.starstore.extensions.hide
import br.com.stone.vianna.starstore.extensions.show
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.baseClasses.BaseActivity
import br.com.stone.vianna.starstore.entity.PaymentRequest
import kotlinx.android.synthetic.main.activity_credit_card.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CreditCardActivity : BaseActivity(), CardContract.View {



    companion object {
        const val CHECKOUT_VALUE = "CHECKOUT_VALUE"
    }

    val value by lazy {
        intent.getIntExtra(CHECKOUT_VALUE, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)

    }



}