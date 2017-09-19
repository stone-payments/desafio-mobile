package io.hasegawa.presentation.screen_transactions

import io.hasegawa.presentation.base.BaseMvpView
import io.reactivex.Observable

object TransactionsContract {
    interface View : BaseMvpView<ViewState> {
        fun back(): Observable<Unit>
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Loaded(val transactions: List<Transaction>) : ViewState()
    }

    sealed class StateEvent {
        object Loading : StateEvent()
        data class Loaded(val transactions: List<Transaction>) : StateEvent()
    }

    interface Navigator {
        fun goBackToStore()
    }
}