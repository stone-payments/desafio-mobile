package br.com.stone.vianna.starstore.feature.card

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
        fun init(value: Int)
        fun onCheckoutButtonClicked(cardNumber: String, cardHolderName: String, cardExpDate: String,
                                    cardCvv: String)

        fun clearEvents()
    }
}