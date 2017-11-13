package douglasspgyn.com.github.desafiostone.ui.checkout

/**
 * Created by Douglas on 13/11/17.
 */

interface CheckoutContract {
    interface View {
        fun updateTotalProduct(total: String)
    }

    interface Presenter {
        fun calculateTotalProduct()
    }
}