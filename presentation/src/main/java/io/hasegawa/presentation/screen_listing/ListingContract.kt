package io.hasegawa.presentation.screen_listing

import io.hasegawa.presentation.base.BaseMvpView
import io.reactivex.Observable
import java.io.Serializable


object ListingContract {
    enum class ListingErrorType {
        ConnectionIssue,
        Unknown
    }

    data class Product(
            val id: String,
            val thumbnailUrl: String,
            val title: String,
            val price: Long,
            val seller: String,
            val inCart: Boolean) : Serializable


    interface View : BaseMvpView<ViewState> {
        fun goToCartScreen(): Observable<Unit>

        fun addToCart(): Observable<Product>
        fun changedFilter(): Observable<String>
    }

    interface ViewCommon {
        val numberInCart: Int
        val filter: String
    }

    sealed class ViewState : ViewCommon {
        data class Loading(
                override val numberInCart: Int,
                override val filter: String) : ViewState()

        data class Error(
                val type: ListingErrorType,
                override val numberInCart: Int,
                override val filter: String) : ViewState()

        data class Okay(
                val products: List<Product>,
                override val numberInCart: Int,
                override val filter: String) : ViewState()
    }

    sealed class StateEvent {
        object Fetching : StateEvent()
        data class ErrorWhileFetching(val type: ListingErrorType) : StateEvent()
        data class NewProductList(val products: List<Product>) : StateEvent()
        data class SetNumberInCart(val number: Int) : StateEvent()
        data class FilterChanged(val filter: String) : StateEvent()
    }

    interface Navigator {
        fun goToCart()
    }
}