package br.com.stone.vianna.starstore.view.card

import android.util.Log
import br.com.stone.vianna.starstore.entity.PaymentRequest
import br.com.stone.vianna.starstore.extensions.*

class CardPresenter(private val view: CardContract.View) : CardContract.Presenter {

    var value = 0

    override fun init(value: Int) {
        this.value = value
    }

    override fun onCheckoutButtonClicked(paymentRequest: PaymentRequest) {

        var isFormValid = true

        when {
            paymentRequest.cardNumber.isEmpty()
                    || !paymentRequest.cardNumber.isValidCardLength() -> {
                isFormValid = isFormValid && false
            }
            else -> {
                isFormValid = isFormValid && true
            }
        }

        when {
            paymentRequest.expirationDate.isEmpty()
                    || !validateCardExpiryDate(paymentRequest.expirationDate)
                    || isDateExpired(paymentRequest.expirationDate) -> {
                isFormValid = isFormValid && false
            }
            else -> {
                isFormValid = isFormValid && true
            }
        }

        when {
            paymentRequest.cardHolder.isEmpty()
                    || !paymentRequest.cardHolder.isValidNameLength() -> {
                isFormValid = isFormValid && false
            }
            else -> {
                isFormValid = isFormValid && true
            }
        }

        when {
            paymentRequest.securityCode.isEmpty()
                    || !paymentRequest.securityCode.isValidCVVLength() -> {
                isFormValid = isFormValid && false
            }
            else -> {
                isFormValid = isFormValid && true
            }
        }

        if (isFormValid) {
            Log.d("FORM", "FORM IS VALID!")
        }else{
            Log.d("FORM", "FORM IS NOT VALID!")
        }

    }


}