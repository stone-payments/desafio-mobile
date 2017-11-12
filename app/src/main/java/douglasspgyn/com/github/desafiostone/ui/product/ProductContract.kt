package douglasspgyn.com.github.desafiostone.ui.product

/**
 * Created by Douglas on 12/11/17.
 */

interface ProductContract {
    interface View {
        fun populateView()
        fun productAdddedToCart()
        fun productFailedToCart()
    }

    interface Presenter {
        fun addToCart()
    }
}