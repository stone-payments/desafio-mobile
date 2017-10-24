package com.stone.starwarsstore

import com.stone.starwarsstore.model.Product

object Cart {
    private val mProducts = mutableListOf<Product?>()

    fun addProduct(product: Product?) {
        mProducts.add(product)
    }

    fun removeProduct(product: Product?) {
        mProducts.remove(product)
    }

    fun changeProductAmount(position: Int, amount: Int) {
        mProducts.get(position)?.amount = amount
    }

    fun clearCart() {
        mProducts.clear()
    }

    fun isCartEmpty() = mProducts.isEmpty()

    fun getCartProducts() = mProducts.toMutableList()

    fun getTotalPrice() : Long? {
        var total: Long? = 0
        for (product: Product? in mProducts) {
              total = (product?.price!! * product.amount!!) + total!!
        }
        return total
    }
}