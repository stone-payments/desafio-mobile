package com.uzias.starwarsshop.checkout.presentation.presenter

import com.uzias.starwarsshop.checkout.presentation.mapper.PresentationCreditCardMapper
import com.uzias.starwarsshop.checkout.presentation.model.PresentationCreditCard
import com.uzias.starwarsshop.checkout.presentation.navigation.CheckoutNavigation
import com.uzias.starwarsshop.checkout.presentation.view.CheckoutView
import com.uzias.starwarsshop.core.behaviours.BehavioursCoordinator
import com.uzias.starwarsshop.core.presentation.BasePresenter
import com.uzias.starwarsshop.core.presentation.lifecycles.LifecycleStrategist
import com.uzias.starwarsshop.transactions.domain.model.CreditCard
import com.uzias.starwarsshop.transactions.domain.usecase.RegisterTransaction
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy

interface CheckoutPresenter: BasePresenter {
    fun clickedButtonCheckout()
    fun resultCreditCard(presentationCreditCard: PresentationCreditCard)
    fun clickedButtonTryAgain()
}

class CheckoutPresenterImpl(private val navigation: CheckoutNavigation,
                            private val view: CheckoutView,
                            private val totalPrice: Double,
                            private val registerTransaction: RegisterTransaction,
                            private val coordinator: BehavioursCoordinator<Any>,
                            private val ioScheduler: Scheduler,
                            private val uiScheduler: Scheduler,
                            private val strategist: LifecycleStrategist) : CheckoutPresenter {

    init {
        view.setTotalPrice(totalPrice)
    }
    private var creditCard: CreditCard? = null

    override fun clickedButtonCheckout() {
        if (creditCard != null){
            registerTransaction()
        }else {
            navigation.goToAddCreditCard()
        }
    }

    private fun registerTransaction(){
        val register = registerTransaction.setField(creditCard!!, totalPrice)
                .run()
                .compose(coordinator)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnNext{ view.finishActivityWithResultOk() }
                .subscribeBy(onError = {})
        strategist.applyStrategy(register)
    }

    override fun resultCreditCard(presentationCreditCard: PresentationCreditCard) {
        creditCard = PresentationCreditCardMapper.transformFrom(presentationCreditCard)
        view.setCreditCard(presentationCreditCard)
        view.changeTextButtonCheckoutToConfirm()
    }

    override fun clickedButtonTryAgain() {
        registerTransaction()
    }

}
