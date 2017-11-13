package douglasspgyn.com.github.desafiostone.ui.checkout

import douglasspgyn.com.github.desafiostone.application.App
import douglasspgyn.com.github.desafiostone.application.App.Companion.orderDao
import douglasspgyn.com.github.desafiostone.application.App.Companion.productDao
import douglasspgyn.com.github.desafiostone.business.controller.OrderController
import douglasspgyn.com.github.desafiostone.business.model.Order
import douglasspgyn.com.github.desafiostone.business.model.OrderRequest
import douglasspgyn.com.github.desafiostone.business.network.RequestCallback
import douglasspgyn.com.github.desafiostone.common.extensions.toCurrency
import okhttp3.ResponseBody

/**
 * Created by Douglas on 13/11/17.
 */

class CheckoutPresenter(val view: CheckoutContract.View) : CheckoutContract.Presenter {

    var totalValue: Double = 0.0

    override fun calculateTotalProduct() {
        val dbProducts = App.productDao?.getProducts()
        if (dbProducts != null && dbProducts.isNotEmpty()) {
            totalValue = dbProducts.sumByDouble { it.price * it.quantity }
            view.updateTotalProduct(totalValue.toCurrency())
        } else {
            view.updateTotalProduct(totalValue.toCurrency())
        }
    }

    override fun createOrder(cardNumber: String, cardCvv: String, cardHolderName: String, cardExpiresDate: String) {
        view.creatingOrder()

        val orderRequest = OrderRequest(cardNumber,
                totalValue.toInt(),
                cardCvv.toInt(),
                cardHolderName,
                cardExpiresDate)

        OrderController().createOrder(orderRequest, object : RequestCallback<ResponseBody> {
            override fun onSuccess(t: ResponseBody) {
                val order = Order(orderRequest.value, System.currentTimeMillis(),
                        getLastDigits(orderRequest.cardNumber), orderRequest.cardHolderName)
                orderDao?.saveOrder(order)
                productDao?.deleteAll()
                view.orderCreated()
            }

            override fun onError() {
                view.orderFailed()
            }

        })
    }

    private fun getLastDigits(cardNumber: String) = cardNumber.subSequence(cardNumber.length - 4, cardNumber.length).toString()
}