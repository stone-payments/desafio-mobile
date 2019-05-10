package br.com.stone.vianna.starstore.view.card

import br.com.stone.vianna.starstore.entity.PaymentRequest


class CardContract {

    interface View {

        fun displayCardNumberError()
        fun hideCardNumberError()
        fun displayCardExpirationDateError()
        fun hideCardExpirationDateError()
        fun displayCardCvvError()
        fun hideCardCvvError()
        fun displayCardHolderError()
        fun hideCardHolderError()
        fun displayProgressBar()
        fun hideProgressBar()
        fun returnToStore()
    }

    interface Presenter {
        fun onCheckoutButtonClicked(paymentRequest: PaymentRequest)
        fun init(value: Int)
    }
}