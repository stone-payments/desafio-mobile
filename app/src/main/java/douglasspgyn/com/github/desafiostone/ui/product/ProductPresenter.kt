package douglasspgyn.com.github.desafiostone.ui.product

import douglasspgyn.com.github.desafiostone.application.App.Companion.productDao
import douglasspgyn.com.github.desafiostone.business.model.Product

/**
 * Created by Douglas on 12/11/17.
 */

class ProductPresenter(val view: ProductContract.View) : ProductContract.Presenter {

    lateinit var product: Product

    override fun addToCart() {
        try {
            val dbProduct = productDao?.getProduct(product.title)

            if (dbProduct == null) {
                productDao?.saveProduct(product)
            } else {
                dbProduct.quantity += 1
                productDao?.saveProduct(dbProduct)
            }

            view.productAddedToCart()
        } catch (e: Exception) {
            view.productFailedToCart()
        }
    }
}