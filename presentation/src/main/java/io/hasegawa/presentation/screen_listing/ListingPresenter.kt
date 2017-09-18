package io.hasegawa.presentation.screen_listing

import io.hasegawa.presentation.base.BasePresenter
import io.hasegawa.presentation.screen_listing.ListingContract.ListingErrorType.ConnectionIssue
import io.hasegawa.presentation.screen_listing.ListingContract.ListingErrorType.Unknown
import io.hasegawa.presentation.screen_listing.ListingContract.StateEvent
import io.hasegawa.presentation.screen_listing.ListingContract.ViewState
import io.hasegawa.stonesafio.domain.common.log.logi
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class ListingPresenter(val navigator: ListingContract.Navigator,
                       val fetcherUC: ListingFetcherUC,
                       val addToCartUC: ListingAddToCartUC)
    : BasePresenter<ListingContract.View, ListingContract.ViewState, ListingContract.StateEvent>() {
    override fun initialViewState() = ListingContract.ViewState.Loading(numberInCart = 0, filter = "")

    override fun handleNavigationIntents(): Completable {
        val goToCart = intent { it.goToCartScreen() }.doOnNext { navigator.goToCart() }
        val goToTransactions = intent { it.goToTransactionsScreen() }.doOnNext { navigator.goToTransactions() }
        return Observable.merge(goToCart, goToTransactions).ignoreElements()
    }

    override fun bindViewIntents(): Observable<StateEvent> {
        val filterChangesIntent = intent { it.changedFilter() }
                .distinctUntilChanged()
                .throttleLast(200, TimeUnit.MILLISECONDS)

        val buyObs = addToCartUC.buildObservable(
                ListingAddToCartUC.Params(intent { it.addToCart().map { it.id } }))
                .doOnNext { logi { "Buying: $it" } }

        val fetchObs = fetcherUC.buildObservable(ListingFetcherUC.Params(filterChangesIntent))
                .map {
                    when (it) {
                        is ListingFetcherUC.Result.Success -> StateEvent.NewProductList(it.list)
                        is ListingFetcherUC.Result.NoInternet -> StateEvent.ErrorWhileFetching(ConnectionIssue)
                        is ListingFetcherUC.Result.UnknownError -> StateEvent.ErrorWhileFetching(Unknown)
                    }
                }
                .startWith(StateEvent.Fetching)

        val filterObs = filterChangesIntent.map { StateEvent.FilterChanged(it) as StateEvent }

        return Observable.merge(fetchObs, buyObs, filterObs)
    }

    override fun ViewState.plus(event: StateEvent): ListingContract.ViewState? {
        fun loading() =
                ViewState.Loading(numberInCart = this.numberInCart, filter = this.filter)

        fun error(type: ListingContract.ListingErrorType) =
                ViewState.Error(type = type, numberInCart = this.numberInCart, filter = this.filter)

        fun okay(list: List<ListingContract.Product>): ViewState.Okay {
            val prevState = (this as? ViewState.Okay) ?:
                    ViewState.Okay(emptyList(), numberInCart = this.numberInCart, filter = this.filter)
            return prevState.copy(products = list)
        }

        fun setNumInCartOrFilter(numInCart: Int, filter: String) =
                when (this) {
                    is ViewState.Loading -> this.copy(numberInCart = numInCart, filter = filter)
                    is ViewState.Error -> this.copy(numberInCart = numInCart, filter = filter)
                    is ViewState.Okay -> this.copy(numberInCart = numInCart, filter = filter)
                }

        return when (event) {
            is StateEvent.Fetching -> loading()
            is StateEvent.ErrorWhileFetching -> error(event.type)
            is StateEvent.NewProductList -> okay(event.products)
            is StateEvent.SetNumberInCart -> setNumInCartOrFilter(event.number, this.filter)
            is StateEvent.FilterChanged -> setNumInCartOrFilter(this.numberInCart, event.filter)
        }
    }
}