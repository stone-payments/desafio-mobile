package br.com.ygorcesar.desafiostone.view.transaction

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.*
import br.com.ygorcesar.desafiostone.model.CardInformation
import br.com.ygorcesar.desafiostone.model.Transaction
import com.vicpin.krealmextensions.queryLast
import com.vicpin.krealmextensions.save
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_checkout.*
import java.util.*

class CheckoutFragment : Fragment() {
    val cartPrice by lazy { ShoppingCart.Companion.instance.cartPrice() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_checkout, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_cart_price.text = getString(R.string.cart_price, cartPrice.toCurrencyBRL())
        btn_checkout.setOnClickListener { saveTransaction() }
    }

    fun validFields(): CardInformation? {
        var cardInfo: CardInformation? = null
        if (!edt_card_number.getString().isNullOrEmpty() && !edt_card_holder.getString().isNullOrEmpty()
                && !edt_card_cvv.getString().isNullOrEmpty() && edt_card_cvv.getString().length == 3
                && !edt_exp_date.getString().isNullOrEmpty()) {
            cardInfo = CardInformation(edt_card_number.getString(), cartPrice,
                    edt_card_cvv.getString().toInt(), edt_card_holder.getString(), edt_exp_date.getString())
        } else {
            toast(R.string.all_fields_required)
        }
        return cardInfo
    }

    fun saveTransaction() {
        validFields()?.let {
            val progressChechout = ProgressDialog(context)
            progressChechout.apply {
                isIndeterminate = true
                setMessage(getString(R.string.progress_registering_order))
                show()
            }

            ApiDesafioMobile().api.checkoutOrder(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ it -> toast(it.message) },
                            {
                                error ->
                                error.printStackTrace()
                                progressChechout.dismiss()
                                toast(R.string.problem)
                            },
                            {
                                val t = Transaction().queryLast()
                                val id = if (t != null) t.id + 1 else 1
                                Transaction(id, cartPrice, Date().formatToBrasil(), it.card_number.lastFourDigits(), "Ygor").save()
                                progressChechout.dismiss()
                                activity.onBackPressed()
                                ShoppingCart.Companion.instance.clear()
                            })
        }
    }
}