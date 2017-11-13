package douglasspgyn.com.github.desafiostone.ui.cart

import douglasspgyn.com.github.desafiostone.application.App.Companion.productDao
import douglasspgyn.com.github.desafiostone.common.extensions.toCurrency

/**
 * Created by Douglas on 12/11/17.
 */

class CartPresenter(val view: CartContract.View) : CartContract.Presenter {

    override fun getCartProducts() {
        try {
            val dbProducts = productDao?.getProducts()
            if (dbProducts != null) {
                if (dbProducts.isNotEmpty()) {
                    view.cartLoaded(dbProducts)
                } else {
                    view.cartEmpty()
                }
            } else {
                view.cartFailed()
            }
        } catch (e: Exception) {
            view.cartFailed()
        }
    }

    override fun calculateTotalProduct() {
        val dbProducts = productDao?.getProducts()
        var total = 0.0
        if (dbProducts != null && dbProducts.isNotEmpty()) {
            total = dbProducts.sumByDouble { it.price * it.quantity }
            view.updateTotalProduct(total.toCurrency())
        } else {
            view.updateTotalProduct(total.toCurrency())
        }
    }
}