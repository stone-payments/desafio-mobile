package io.hasegawa.presentation.screen_test

import io.hasegawa.presentation.base.BaseMvpView
import io.reactivex.Observable


object TestContract {
    interface View : BaseMvpView<ViewState> {
        fun reloadData(): Observable<Unit>
        fun nextScreen(): Observable<Unit>
    }

    sealed class ViewState {
        object LoadingData : ViewState()
        data class Okay(val text: String = "") : ViewState()
        data class Error(val error: String = "") : ViewState()
    }

    sealed class StateEvent {
        object LoadingData : StateEvent()
        data class TextLoaded(val value: String) : StateEvent()
        data class TextError(val error: String) : StateEvent()
    }

    interface Navigator {
        fun goToNextScreen()
    }
}