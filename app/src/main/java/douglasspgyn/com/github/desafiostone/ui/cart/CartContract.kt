package douglasspgyn.com.github.desafiostone.ui.cart

import douglasspgyn.com.github.desafiostone.business.model.Product


/**
 * Created by Douglas on 12/11/17.
 */

interface CartContract {
    interface View {
        fun cartLoaded(products: List<Product>)
        fun cartEmpty()
        fun cartFailed()
        fun cartCleared()
        fun updateTotalProduct(total: String)
    }

    interface Presenter {
        fun getCartProducts()
        fun calculateTotalProduct()
        fun clearCart()
    }
}