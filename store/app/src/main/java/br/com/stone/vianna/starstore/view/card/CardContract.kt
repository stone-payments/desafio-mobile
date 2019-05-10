package br.com.stone.vianna.starstore.view.card

import br.com.stone.vianna.starstore.entity.PaymentRequest


class CardContract {

    interface View {


    }

    interface Presenter {
        fun onCheckoutButtonClicked(paymentRequest: PaymentRequest)
        fun init(value: Int)
    }
}