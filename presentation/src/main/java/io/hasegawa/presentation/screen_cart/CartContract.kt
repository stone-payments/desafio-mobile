package io.hasegawa.presentation.screen_cart

import io.hasegawa.presentation.base.BaseMvpView
import io.hasegawa.stonesafio.domain.cart.CartProduct
import io.reactivex.Observable

object CartContract {
    interface View : BaseMvpView<ViewState> {
        fun confirmCartList(): Observable<Unit>
        fun confirmPayment(): Observable<Unit>
        fun back(): Observable<Unit>

        /**
         * Returns the id of the product to be removed.
         */
        fun removeItemFromCart(): Observable<String>

        fun changesCCNumber(): Observable<String>
        fun changesCCName(): Observable<String>
        fun changesCCVerificationNumber(): Observable<String>
        fun changesExpDateMonth(): Observable<String>
        fun changesExpDateYear(): Observable<String>
    }

    sealed class SummaryViewState {
        object Loading : SummaryViewState()
        data class Success(val value: Long) : SummaryViewState()
        object Error : SummaryViewState()
    }

    enum class Page { Cart, Payment, Summary }

    data class ViewState(
            val currentPage: Page = Page.Cart,

            // First screen page
            val loadingProducts: Boolean = true,
            val errorLoadingProduct: Boolean = false,
            val products: List<CartProduct> = emptyList(),
            val productsTotal: Long = 0,
            val canProceedToPaymentScreen: Boolean = false,

            // Second screen page
            val ccInfo: CartCCInfo = CartCCInfo(),
            val canConfirmPayment: Boolean = false,

            // Third screen page
            val summaryViewState: SummaryViewState = SummaryViewState.Loading
    )

    sealed class StateEvent {
        data class SwitchPage(val page: Page) : StateEvent()

        data class SetProducts(val products: List<CartProduct>) : StateEvent()
        object ShowLoadingProductsError : StateEvent()
        data class ProductsTotal(val total: Long) : StateEvent()
        data class SetCanGoToPayment(val allow: Boolean) : StateEvent()

        data class SetCCInfo(val info: CartCCInfo) : StateEvent()
        data class SetCanConfirmPayment(val canConfirm: Boolean) : StateEvent()

        object ShowLoadingSummary : StateEvent()
        data class SummarySuccess(val value: Long) : StateEvent()
        object ShowSummaryError : StateEvent()
    }

    interface Navigator {
        fun goBackToStore()
    }
}