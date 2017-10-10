package br.com.wagnerrodrigues.starwarsstore.domain.interactor

import br.com.wagnerrodrigues.starwarsstore.data.dao.CartItemDao
import br.com.wagnerrodrigues.starwarsstore.data.dao.TransactionDao
import br.com.wagnerrodrigues.starwarsstore.data.service.PaymentService
import br.com.wagnerrodrigues.starwarsstore.domain.entity.CartItem
import br.com.wagnerrodrigues.starwarsstore.domain.entity.Transaction
import br.com.wagnerrodrigues.starwarsstore.domain.event.PaymentSuccessfulEvent
import br.com.wagnerrodrigues.starwarsstore.domain.event.PaymentUnsuccessfulEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.event.ErrorEvent
import br.com.wagnerrodrigues.starwarsstore.presentation.event.PurchaseFinishedEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class PaymentInteractor : Interactor(){

    private val paymentService : PaymentService = PaymentService()

    private val cartDao: CartItemDao? = CartItemDao.getInstance();
    private val transactionDao: TransactionDao? = TransactionDao.getInstance();

    fun removeFromCart(cartItem: CartItem) {
        cartDao?.delete(cartItem)
    }

    fun finishPurchase(transaction: Transaction) {
        paymentService.sendPaymentData(transaction)
    }

    @Subscribe
    fun onPaymentSuccessful(paymentSuccessfulEvent: PaymentSuccessfulEvent){
        transactionDao?.create(paymentSuccessfulEvent.transaction);
        cartDao?.clear()
        EventBus.getDefault().post(PurchaseFinishedEvent())
    }

    @Subscribe
    fun onPaymentUnsuccessful(paymentUnsuccessfulEvent: PaymentUnsuccessfulEvent){
        EventBus.getDefault().post(ErrorEvent(paymentUnsuccessfulEvent.message))
    }
}