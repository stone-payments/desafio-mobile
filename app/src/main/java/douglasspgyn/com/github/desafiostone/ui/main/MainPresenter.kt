package douglasspgyn.com.github.desafiostone.ui.main

import douglasspgyn.com.github.desafiostone.business.controller.ProductController
import douglasspgyn.com.github.desafiostone.business.model.Product
import douglasspgyn.com.github.desafiostone.business.network.RequestCallback

/**
 * Created by Douglas on 12/11/17.
 */

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {

    override fun loadProducts(showLoading: Boolean) {
        if (showLoading) {
            view.showLoading()
        }

        ProductController().getProducts(object : RequestCallback<List<Product>> {
            override fun onSuccess(products: List<Product>) {
                view.productsLoaded(products)
                view.hideLoading()
                view.hideSwipeRefresh()
            }

            override fun onError() {
                view.productsFailed()
                view.hideLoading()
                view.hideSwipeRefresh()
            }

        })
    }
}