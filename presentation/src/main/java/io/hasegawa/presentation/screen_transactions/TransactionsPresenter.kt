package io.hasegawa.presentation.screen_transactions

import io.hasegawa.presentation.base.BasePresenter
import io.hasegawa.presentation.screen_transactions.TransactionsContract.Navigator
import io.hasegawa.presentation.screen_transactions.TransactionsContract.StateEvent
import io.hasegawa.presentation.screen_transactions.TransactionsContract.ViewState
import io.reactivex.Completable
import io.reactivex.Observable

class TransactionsPresenter(val navigator: Navigator, val getUC: TransactionsGetUC) :
        BasePresenter<TransactionsContract.View,
                TransactionsContract.ViewState, TransactionsContract.StateEvent>() {
    override fun initialViewState(): ViewState = ViewState.Loading

    override fun handleNavigationIntents(): Completable {
        return intent { it.back() }
                .doOnNext { navigator.goBackToStore() }
                .ignoreElements()
    }

    override fun bindViewIntents(): Observable<StateEvent> = getUC.buildObservable()

    override fun ViewState.plus(event: StateEvent): ViewState? =
            when (event) {
                is StateEvent.Loading -> ViewState.Loading
                is StateEvent.Loaded -> ViewState.Loaded(event.transactions)
            }
}