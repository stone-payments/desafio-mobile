package br.com.ygorcesar.desafiostone.view.transaction

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.*
import br.com.ygorcesar.desafiostone.databinding.FragmentCheckoutBinding
import br.com.ygorcesar.desafiostone.model.CardInformation
import br.com.ygorcesar.desafiostone.model.Transaction
import br.com.ygorcesar.desafiostone.viewmodel.CardInformationViewModel
import com.vicpin.krealmextensions.queryLast
import com.vicpin.krealmextensions.save
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class CheckoutFragment : Fragment() {
    val cartPrice by lazy { ShoppingCart.Companion.instance.cartPrice() }
    lateinit var mBinding: FragmentCheckoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate<FragmentCheckoutBinding>(inflater, R.layout.fragment_checkout, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.tvCartPrice.text = getString(R.string.cart_price, cartPrice.toCurrencyBRL())
        mBinding.card = CardInformationViewModel(CardInformation("", cartPrice, 0, "", ""), {
            saveTransaction()
        })
    }

    fun saveTransaction() {

        mBinding.card?.let {
            if (it.validCard()) {
                val progressChechout = progressDialog(R.string.progress_registering_order)
                it.getCard().let { card ->
                    ApiDesafioMobile().api.checkoutOrder(card)
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
                                        Transaction(id, cartPrice, Date().formatToBrasil(), card.card_number.lastFourDigits(), card.card_holder_name).save()
                                        progressChechout.dismiss()
                                        activity.onBackPressed()
                                        ShoppingCart.Companion.instance.clear()
                                    })
                }
            } else {
                toast(R.string.all_fields_required)
            }
        }
    }
}