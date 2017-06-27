package com.example.pharol.temminstore.shoppingcart

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import java.math.BigDecimal


class ShoppingCartViewModel(val shoppingCartRepository: ShoppingCartRepository): ViewModel() {

    fun plusItem(cart: ShoppingCart) {
        cart.qtdItems +=1
        shoppingCartRepository.save(cart)
    }

    /**
     * Subtract a item form cart, if quantity is 0 delete the item
     */
    fun minusItems(cart: ShoppingCart) {
        cart.qtdItems -= 1
        if (cart.qtdItems == 0){
            shoppingCartRepository.deleteItem(cart)
        } else{
            shoppingCartRepository.save(cart)
        }
    }

    fun getLastCart(): LiveData<List<ShoppingCart>> = shoppingCartRepository.getCart()

    fun clearCart() {
        shoppingCartRepository.delete()
    }

    /**
     * Return total price of items on cart
     */
    fun getCartTotal(): LiveData<BigDecimal> = shoppingCartRepository.getCartTotal()

}
