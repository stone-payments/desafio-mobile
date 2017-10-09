package br.com.wagnerrodrigues.starwarsstore.domain.interactor

import br.com.wagnerrodrigues.starwarsstore.data.dao.CartItemDao
import br.com.wagnerrodrigues.starwarsstore.data.service.ProductService
import br.com.wagnerrodrigues.starwarsstore.domain.entity.CartItem
import br.com.wagnerrodrigues.starwarsstore.domain.entity.Product
import br.com.wagnerrodrigues.starwarsstore.domain.event.ProductsFetchedEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ProductAddedToCartEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ProductsPreparedEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainInteractor : Interactor(){

    private val productService : ProductService = ProductService()

    private val cartDao: CartItemDao? = CartItemDao.getInstance();

    fun prepareProducts(){
        productService.fetchAll()
    }

    fun addProductToCart(product: Product?, quantity: Int) {
        val cartItem = CartItem(product, quantity)
        cartDao?.create(cartItem)
        EventBus.getDefault().post(ProductAddedToCartEvent(product,quantity))
    }

    @Subscribe
    fun onFetchedProducts(productsFetchedEvent: ProductsFetchedEvent){
        EventBus.getDefault().post(ProductsPreparedEvent(productsFetchedEvent.products))
    }



}