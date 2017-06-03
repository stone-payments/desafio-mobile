package com.partiufast.mercadodoimperador.model


import java.util.*

class Store {
    private val cartProducts: ArrayList<Product> = ArrayList<Product>()
    private val availableProducts: ArrayList<Product> = ArrayList<Product>()

    fun setAvailableProducts(products: ArrayList<Product>) {
        availableProducts.addAll(products)
    }

    fun addProductToCart(product: Product) {
        if (cartProducts.size == 0)
            cartProducts.add(product)
        else {
            if (getProductIndexIfContains(product) == -1)
                cartProducts.add(product)
        }
    }

    private fun getProductIndexIfContains(product: Product): Int {
        for (i in cartProducts.indices) {
            if (cartProducts[i].title == (product.title)) {
                cartProducts[i].productCount = product.productCount
                return i
            }
        }
        return -1
    }


    fun getAvailableProducts(): ArrayList<Product> {
        return availableProducts
    }

    fun getCartProducts(): ArrayList<Product> {
        return cartProducts
    }


}


