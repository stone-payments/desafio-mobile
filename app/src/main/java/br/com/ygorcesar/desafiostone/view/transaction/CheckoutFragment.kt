package br.com.ygorcesar.desafiostone.view.transaction

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ygorcesar.desafiostone.R
import br.com.ygorcesar.desafiostone.data.ShoppingCart
import br.com.ygorcesar.desafiostone.data.progressDialog
import br.com.ygorcesar.desafiostone.data.toCurrencyBRL
import br.com.ygorcesar.desafiostone.data.toast
import br.com.ygorcesar.desafiostone.databinding.FragmentCheckoutBinding
import br.com.ygorcesar.desafiostone.model.CardInformation
import br.com.ygorcesar.desafiostone.view.MainActivity
import br.com.ygorcesar.desafiostone.viewmodel.CardInformationViewModel

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
        mBinding.card = CardInformationViewModel(CardInformation("", cartPrice, 0, "", ""), { saveTransaction() })
    }

    fun saveTransaction() {
        mBinding.card?.let {
            if (it.validCard()) {
                val progressCheckout = progressDialog(R.string.progress_registering_order)
                it.checkoutTransaction(onError = {
                    progressCheckout.dismiss()
                    toast(R.string.problem)
                }, onComplete = {
                    toast(R.string.checkout_completed)
                    progressCheckout.dismiss()
                    if(activity is MainActivity){
                        val a = activity as MainActivity
                        a.selectItemMenu(R.id.action_home)
                    }
                })
            } else {
                toast(R.string.all_fields_required)
            }
        }
    }
}