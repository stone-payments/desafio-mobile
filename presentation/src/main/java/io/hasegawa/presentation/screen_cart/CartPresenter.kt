package io.hasegawa.presentation.screen_cart

import io.hasegawa.presentation.base.BasePresenter
import io.hasegawa.presentation.screen_cart.CartContract.Page.Cart
import io.hasegawa.presentation.screen_cart.CartContract.Page.Payment
import io.hasegawa.presentation.screen_cart.CartContract.Page.Summary
import io.hasegawa.presentation.screen_cart.CartContract.StateEvent
import io.hasegawa.presentation.screen_cart.CartContract.SummaryViewState
import io.hasegawa.presentation.screen_cart.CartContract.ViewState
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CartPresenter(private val navigator: CartContract.Navigator,
                    val cartProductsUC: CartProductsUC,
                    val cartRemoveItemUC: CartRemoveItemUC,
                    val cartCCValidationUC: CartCCValidationUC,
                    val cartPayUC: CartPayUC)
    : BasePresenter<CartContract.View, ViewState, StateEvent>() {
    override fun initialViewState(): ViewState = ViewState()

    override fun handleNavigationIntents(): Completable {
        return intent { it.back() }
                .doOnNext { navigator.goBackToStore() }
                .ignoreElements()
    }

    override fun bindViewIntents(): Observable<StateEvent> {
        val pageToPayment = intent { it.confirmCartList() }
                .map { StateEvent.SwitchPage(Payment) }

        val ccValidation = run {
            val params = CartCCValidationUC.Params(
                    number = intent { it.changesCCNumber() },
                    name = intent { it.changesCCName() },
                    cvv = intent { it.changesCCVerificationNumber() },
                    expDateMonth = intent { it.changesExpDateMonth() },
                    expDateYear = intent { it.changesExpDateYear() })
            cartCCValidationUC.buildObservable(params)
        }

        val pay =
                intent { it.confirmPayment() }
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .concatMap { viewStateObservable.take(1) }
                        .concatMap {
                            val params = CartPayUC.Params(it.productsTotal, it.ccInfo)
                            cartPayUC.buildObservable(params)
                        }


        val productsUC = cartProductsUC.buildObservable()
        val removeUC = cartRemoveItemUC
                .buildObservable(intent { it.removeItemFromCart() })

        return Observable.merge(listOf(ccValidation, pageToPayment, productsUC, removeUC, pay))
    }

    override fun ViewState.plus(event: StateEvent): ViewState? = when (event) {
        is StateEvent.SwitchPage ->
            when (event.page) {
                Cart -> copy(currentPage = event.page)
                Payment -> {
                    if (currentPage == Cart && canProceedToPaymentScreen) {
                        copy(currentPage = event.page)
                    } else {
                        null
                    }
                }
                Summary -> {
                    if (currentPage == Payment && canConfirmPayment) {
                        copy(currentPage = event.page)
                    } else {
                        null
                    }
                }
            }

        is StateEvent.SetProducts -> copy(products = event.products, errorLoadingProduct = false, loadingProducts = false)
        is StateEvent.ProductsTotal -> copy(productsTotal = event.total)
        is StateEvent.ShowLoadingProductsError -> copy(loadingProducts = false, errorLoadingProduct = true)
        is StateEvent.SetCanGoToPayment -> copy(canProceedToPaymentScreen = event.allow)

        is StateEvent.SetCCInfo -> copy(ccInfo = event.info)
        is StateEvent.SetCanConfirmPayment -> copy(canConfirmPayment = event.canConfirm)

        is StateEvent.ShowLoadingSummary -> copy(summaryViewState = SummaryViewState.Loading, currentPage = Summary)
        is StateEvent.SummarySuccess -> copy(summaryViewState = SummaryViewState.Success(event.value), currentPage = Summary)
        is StateEvent.ShowSummaryError -> copy(summaryViewState = SummaryViewState.Error, currentPage = Summary)
    }
}