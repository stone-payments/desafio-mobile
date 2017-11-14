package douglasspgyn.com.github.desafiostone.ui.main

import douglasspgyn.com.github.desafiostone.business.model.Product

/**
 * Created by Douglas on 12/11/17.
 */
interface MainContract {

    interface View {
        fun productsLoaded(products: List<Product>)
        fun productsFailed()
        fun showLoading()
        fun hideLoading()
        fun hideSwipeRefresh()
    }

    interface Presenter {
        fun loadProducts(showLoading: Boolean)
    }
}