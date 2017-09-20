package io.hasegawa.presentation.screen_test

import io.hasegawa.presentation.base.BasePresenter
import io.hasegawa.presentation.screen_test.TestContract.StateEvent
import io.hasegawa.presentation.screen_test.TestContract.ViewState
import io.hasegawa.stonesafio.domain.common.log.logd
import io.reactivex.Completable
import io.reactivex.Observable

class TestPresenter(val navigator: TestContract.Navigator, val loaderUC: TestLoaderUC)
    : BasePresenter<TestContract.View, ViewState, StateEvent>() {

    override fun initialViewState(): ViewState = ViewState.LoadingData

    override fun handleNavigationIntents(): Completable {
        return intent { view -> view.nextScreen() }
                .doOnNext { navigator.goToNextScreen() }
                .ignoreElements()
    }

    override fun bindViewIntents(): Observable<StateEvent> {
        val reloadIntent = intent { view -> view.reloadData() }
                .doOnNext { logd { "Intent reload" } }
        return loaderUC.buildObservable(TestLoaderUC.Params(reloadIntent))
    }

    override operator fun ViewState.plus(event: StateEvent): ViewState? {
        fun error(error: String? = null): ViewState.Error {
            val prevState = (this as? ViewState.Error) ?: ViewState.Error()

            return prevState
                    .let { state -> error?.let { state.copy(error = it) } ?: state }
        }

        fun okay(text: String? = null): ViewState.Okay {
            val prevState = (this as? ViewState.Okay) ?: ViewState.Okay()

            return prevState
                    .let { state -> text?.let { state.copy(text = it) } ?: state }
        }

        fun loading() = ViewState.LoadingData

        logd { "Reducer-From: $event" }
        return when (event) {
            is StateEvent.TextError -> error(event.error)
            is StateEvent.LoadingData -> loading()
            is StateEvent.TextLoaded -> okay(event.value)
        }.also { logd { "Reducer-  To: $it" } }
    }
}