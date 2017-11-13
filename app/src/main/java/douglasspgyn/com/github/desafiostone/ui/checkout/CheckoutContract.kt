package douglasspgyn.com.github.desafiostone.ui.checkout

/**
 * Created by Douglas on 13/11/17.
 */

interface CheckoutContract {
    interface View {
        fun updateTotalProduct(total: String)
        fun creatingOrder()
        fun orderCreated()
        fun orderFailed()
    }

    interface Presenter {
        fun calculateTotalProduct()
        fun createOrder(cardNumber: String, cardCvv: String, cardHolderName: String, cardExpiresDate: String)
    }
}