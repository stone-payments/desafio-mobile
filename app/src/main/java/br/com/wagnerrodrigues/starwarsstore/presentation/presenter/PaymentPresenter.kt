package br.com.wagnerrodrigues.starwarsstore.presentation.presenter

import br.com.wagnerrodrigues.starwarsstore.domain.entity.Transaction
import br.com.wagnerrodrigues.starwarsstore.domain.interactor.PaymentInteractor
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ErrorEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.event.PurchaseFinishedEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment.PaymentFragment
import kotlinx.android.synthetic.main.fragment_payment.*
import org.greenrobot.eventbus.Subscribe
import java.util.*

/**
 * Presenter da main activity
 */

class PaymentPresenter(private val fragment: PaymentFragment) : Presenter() {

    private val interactor : PaymentInteractor = PaymentInteractor()

    fun finishPurchase() {
        try {
            var transaction = Transaction()
            transaction.cardHolderName = fragment.et_name.text.toString()
            transaction.cardNumber = fragment.et_card_number.text.toString()
            transaction.cvv = fragment.et_cvv.text.toString()
            transaction.expDate = fragment.et_exp_month.toString() + "/" + fragment.et_exp_year.toString()
            transaction.value = fragment.tx_total_payment.toString()
            transaction.dateTime = Date()
            transaction.lastDigits = transaction.cardNumber?.substring(transaction.cardNumber?.length!!.minus(4))

            interactor.finishPurchase(transaction);
        }catch(e : Exception){
            fragment.onUnsuccessfullPurchase(ErrorEvent("Falha ao realizar a compra"))
        }
    }

    @Subscribe
    fun onPurchaseFinished(purchaseFinishedEvent: PurchaseFinishedEvent){
        fragment.onSuccessfullPurchase()
    }

    @Subscribe
    fun onPurchaseFinished(errorEvent: ErrorEvent){
        fragment.onUnsuccessfullPurchase(errorEvent)


    }

    override fun registerEvents() {
        super.registerEvents()
        interactor.registerEvents()
    }

    override fun unregisterEvents() {
        super.unregisterEvents()
        interactor.unregisterEvents()
    }
}