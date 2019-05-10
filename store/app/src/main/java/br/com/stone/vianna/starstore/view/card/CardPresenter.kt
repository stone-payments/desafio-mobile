package br.com.stone.vianna.starstore.view.card

import android.util.Log
import br.com.stone.vianna.starstore.entity.ItemDao
import br.com.stone.vianna.starstore.entity.PaymentRequest
import br.com.stone.vianna.starstore.entity.PaymentTransaction
import br.com.stone.vianna.starstore.entity.TransactionDao
import br.com.stone.vianna.starstore.extensions.*
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CardPresenter(private val view: CardContract.View,
                    private val paymentRepository: PaymentRepository,
                    private val transactionDao: TransactionDao,
                    private val itemDao: ItemDao) : CardContract.Presenter {

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
        paymentRepository.checkout(paymentRequest,
                { onSuccessCheckout(it) },
                { onErrorCheckout(it) })
    }

    private fun onSuccessCheckout(transaction: PaymentTransaction) {
        view.hideProgressBar()

        Completable
                .fromCallable { transactionDao.insertTransaction(transaction) }
                .doOnComplete { itemDao.removeItems() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { view.returnToStore() },
                        { Log.d("Transaction Error", "Insert Error") }
                )
    }

    private fun onErrorCheckout(error: String) {
        view.hideProgressBar()
        Log.e("ERRO", error)
    }

}