package douglasspgyn.com.github.desafiostone.ui.product

import douglasspgyn.com.github.desafiostone.business.model.Product

/**
 * Created by Douglas on 12/11/17.
 */

class ProductPresenter(val view: ProductContract.View) : ProductContract.Presenter {

    lateinit var product: Product

    override fun addToCart() {

    }
}