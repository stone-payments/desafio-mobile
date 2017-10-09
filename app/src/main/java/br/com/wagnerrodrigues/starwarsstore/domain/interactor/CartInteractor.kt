package br.com.wagnerrodrigues.starwarsstore.domain.interactor

import br.com.wagnerrodrigues.starwarsstore.data.dao.CartItemDao
import br.com.wagnerrodrigues.starwarsstore.data.service.ProductService
import br.com.wagnerrodrigues.starwarsstore.domain.entity.Cart
import br.com.wagnerrodrigues.starwarsstore.domain.entity.CartItem
import br.com.wagnerrodrigues.starwarsstore.presentation.event.CartItemsPreparedEvent
import org.greenrobot.eventbus.EventBus

class CartInteractor : Interactor(){

    private val productService : ProductService = ProductService()

    private val cartDao: CartItemDao? = CartItemDao.getInstance();

    fun prepareCart(){

        val cart = Cart()
        cart.cartItems = cartDao?.fetchAll();

        cart.cartItems?.forEach{
            cart.totalPurchase += it.price!!
        }

        val cartsPreparedEvent = CartItemsPreparedEvent(cart)
        EventBus.getDefault().post(cartsPreparedEvent)
    }

    fun removeFromCart(cartItem: CartItem) {
        cartDao?.delete(cartItem)
        prepareCart()
    }
}