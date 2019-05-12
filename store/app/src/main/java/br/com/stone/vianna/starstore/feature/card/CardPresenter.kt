package br.com.stone.vianna.starstore.feature.card

import br.com.stone.vianna.starstore.entity.PaymentRequest
import br.com.stone.vianna.starstore.entity.PaymentTransaction
import br.com.stone.vianna.starstore.feature.itemList.ItemListRepository
import br.com.stone.vianna.starstore.extensions.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class CardPresenter(private val view: CardContract.View,
                    private val paymentRepository: PaymentRepository,
                    private val itemListRepository: ItemListRepository) : CardContract.Presenter {

    private var value = 0

    private val compositeDisposable = CompositeDisposable()

    override fun init(value: Int) {
        this.value = value
    }

    override fun onCheckoutButtonClicked(cardNumber: String, cardHolderName: String,
                                         cardExpDate: String, cardCvv: String) {

        var isFormValid = true

        val paymentRequest = PaymentRequest(cardNumber = cardNumber,
                cardHolder = cardHolderName,
                expirationDate = cardExpDate,
                securityCode = cardCvv,
                value = this.value)

        when {
            paymentRequest.cardNumber.isEmpty()
                    || !paymentRequest.cardNumber.isValidCardLength() -> {
                isFormValid = isFormValid && false
                view.displayCardNumberError()
            }
            else -> {
                isFormValid = isFormValid && true
                view.hideCardNumberError()
            }
        }

        when {
            paymentRequest.expirationDate.isEmpty()
                    || !validateCardExpiryDate(paymentRequest.expirationDate)
                    || isDateExpired(paymentRequest.expirationDate) -> {
                isFormValid = isFormValid && false
                view.displayCardExpirationDateError()
            }
            else -> {
                isFormValid = isFormValid && true
                view.hideCardExpirationDateError()
            }
        }

        when {
            paymentRequest.cardHolder.isEmpty()
                    || !paymentRequest.cardHolder.isValidNameLength() -> {
                isFormValid = isFormValid && false
                view.displayCardHolderError()
            }
            else -> {
                isFormValid = isFormValid && true
                view.hideCardHolderError()
            }
        }

        when {
            paymentRequest.securityCode.isEmpty()
                    || !paymentRequest.securityCode.isValidCVVLength() -> {
                isFormValid = isFormValid && false
                view.displayCardCvvError()
            }
            else -> {
                isFormValid = isFormValid && true
                view.hideCardCvvError()
            }
        }

        if (isFormValid) {
            performCheckout(paymentRequest)
        }

    }

    private fun performCheckout(paymentRequest: PaymentRequest) {

        view.displayProgressBar()

        paymentRepository.checkout(paymentRequest)
                .subscribe({
                    onSuccessCheckout(it)
                }, {
                    onErrorCheckout(it.message.toString())
                })
                .addTo(compositeDisposable)

    }

    private fun onSuccessCheckout(transaction: PaymentTransaction) {
        view.hideProgressBar()
        paymentRepository.saveTransactionLocally(transaction)
                .subscribe { removeItemsFromCart() }
                .addTo(compositeDisposable)
    }

    private fun removeItemsFromCart() {
        itemListRepository.removeItems()
                .subscribe { view.returnToStore() }
                .addTo(compositeDisposable)
    }

    private fun onErrorCheckout(error: String) {
        view.hideProgressBar()
        view.showCheckoutError(error)
    }

    override fun clearEvents() {
        compositeDisposable.clear()
    }
}