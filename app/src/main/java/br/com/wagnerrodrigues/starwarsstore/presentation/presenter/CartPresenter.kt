package br.com.wagnerrodrigues.starwarsstore.presentation.presenter

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import br.com.wagnerrodrigues.starwarsstore.domain.interactor.CartInteractor
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ButtonRemoveFromCartPressedEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.event.CartItemsPreparedEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.view.adapter.CartCardsProductAdapter
import br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment.CartFragment
import kotlinx.android.synthetic.main.fragment_cart.*
import org.greenrobot.eventbus.Subscribe
import java.text.NumberFormat

/**
 * Presenter da main activity
 */

class CartPresenter(private val fragment: CartFragment) : Presenter() {

    private val interactor : CartInteractor = CartInteractor()

    fun prepareCart() {
       interactor.prepareCart()
    }

    @Subscribe
    fun onCartPrepared(cartItemsPreparedEvent: CartItemsPreparedEvent){
        fragment.rv_product_list.layoutManager = LinearLayoutManager(fragment.context, LinearLayout.VERTICAL, false)
        (fragment.rv_product_list.adapter as CartCardsProductAdapter).update(cartItemsPreparedEvent.cart.cartItems?.toMutableList())
        fragment.tx_total_purchase.text = NumberFormat.getCurrencyInstance().format(cartItemsPreparedEvent.cart.totalPurchase);

    }

    @Subscribe
    fun onButtonRemoveFromCartPressed(buttonRemoveFromCartPressedEvent : ButtonRemoveFromCartPressedEvent){
        interactor.removeFromCart(buttonRemoveFromCartPressedEvent.cartItem)
    }
}