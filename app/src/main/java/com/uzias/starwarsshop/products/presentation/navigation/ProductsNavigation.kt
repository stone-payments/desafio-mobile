package com.uzias.starwarsshop.products.presentation.navigation

import com.uzias.starwarsshop.checkout.presentation.view.CheckoutActivity
import com.uzias.starwarsshop.products.presentation.view.ProductsFragment

interface ProductsNavigation {
    fun goToCheckout(totalPrice: Double)
}

class ProductsNavigationImpl(private var productsFragment: ProductsFragment): ProductsNavigation {

    override fun goToCheckout(totalPrice: Double) {
        val intent = CheckoutActivity.newInstance(productsFragment.context, totalPrice)
        productsFragment.activity.startActivityForResult(intent, CheckoutActivity.CHECKOUT_REQUEST_CODE)
    }

}