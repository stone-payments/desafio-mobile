package douglasspgyn.com.github.desafiostone.ui.product

/**
 * Created by Douglas on 12/11/17.
 */

interface ProductContract {
    interface View {
        fun populateView()
    }

    interface Presenter {
        fun addToCart()
    }
}