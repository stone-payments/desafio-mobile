package io.hasegawa.stonesafio.screen_listing

import com.bluelinelabs.conductor.RouterTransaction
import io.hasegawa.presentation.screen_listing.ListingContract
import io.hasegawa.stonesafio.screen_cart.CartController
import io.hasegawa.stonesafio.screen_transactions.TransactionsController

class ListingNavigator(val controller: ListingController) : ListingContract.Navigator {
    override fun goToCart() {
        val router = controller.router
        val cartController = CartController()
        router.pushController(RouterTransaction.with(cartController))
    }

    override fun goToTransactions() {
        val router = controller.router
        val transactionsController = TransactionsController()
        router.pushController(RouterTransaction.with(transactionsController))
    }
}