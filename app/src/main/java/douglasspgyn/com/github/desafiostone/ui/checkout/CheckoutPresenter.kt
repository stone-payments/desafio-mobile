package douglasspgyn.com.github.desafiostone.ui.checkout

import douglasspgyn.com.github.desafiostone.application.App
import douglasspgyn.com.github.desafiostone.common.extensions.toCurrency

/**
 * Created by Douglas on 13/11/17.
 */

class CheckoutPresenter (val view:CheckoutContract.View) : CheckoutContract.Presenter {

    override fun calculateTotalProduct() {
        val dbProducts = App.productDao?.getProducts()
        var total = 0.0
        if (dbProducts != null && dbProducts.isNotEmpty()) {
            total = dbProducts.sumByDouble { it.price * it.quantity }
            view.updateTotalProduct(total.toCurrency())
        } else {
            view.updateTotalProduct(total.toCurrency())
        }
    }

}