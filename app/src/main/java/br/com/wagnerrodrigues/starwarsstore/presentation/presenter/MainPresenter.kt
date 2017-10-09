package br.com.wagnerrodrigues.starwarsstore.presentation.presenter

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import br.com.wagnerrodrigues.starwarsstore.domain.interactor.MainInteractor
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ButtonAddToCartPressedEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ProductAddedToCartEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ProductsPreparedEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.view.adapter.CardsProductAdapter
import br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment.MainFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.greenrobot.eventbus.Subscribe
import java.math.BigDecimal

/**
 * Presenter da main activity
 */

class MainPresenter (private val fragment: MainFragment) : Presenter() {

    private val interactor : MainInteractor = MainInteractor()

    fun prepareProducts() {
        interactor.prepareProducts()
    }

    @Subscribe
    fun onProductsPrepared(productsPreparedEvent: ProductsPreparedEvent){
        productsPreparedEvent.products?.forEach {
            val formatedPrice = StringBuilder(it.price.toString()).insert(it.price.toString().length - 2, ".").toString()
            it.price =  BigDecimal(formatedPrice)
        }

        fragment.rv_product.adapter = CardsProductAdapter(fragment.context, productsPreparedEvent.products?.toMutableList())
        fragment.rv_product.layoutManager = LinearLayoutManager(fragment.context, LinearLayout.VERTICAL, false)
    }

    @Subscribe
    fun onButtonAddToCartPressed(buttonAddToCartPressedEvent: ButtonAddToCartPressedEvent){
        interactor.addProductToCart(buttonAddToCartPressedEvent.product, buttonAddToCartPressedEvent.quantity)
    }

    @Subscribe
    fun onProductAddedToCart(productAddedToCartEvent: ProductAddedToCartEvent){
        fragment.onProductAddedToCart(productAddedToCartEvent.quantity)
    }

    override fun registerEvents() {
        super.registerEvents()
        interactor.registerEvents()
    }

    override fun unregisterEvents() {
        super.unregisterEvents()
        interactor.unregisterEvents()
    }

}