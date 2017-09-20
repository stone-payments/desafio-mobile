package io.hasegawa.presentation.screen_test

import io.hasegawa.presentation.screen_test.TestContract.StateEvent.TextError
import io.hasegawa.presentation.screen_test.TestContract.StateEvent.TextLoaded
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.hasegawa.stonesafio.domain.test.TestLoadUC
import io.reactivex.Observable


open class TestLoaderUC(val loaderUC: TestLoadUC)
    : UseCase<TestContract.StateEvent, TestLoaderUC.Params>() {
    data class Params(val reloadObs: Observable<Unit>)

    override fun buildObservable(params: Params?): Observable<TestContract.StateEvent> {
        requireNotNull(params)
        return params!!.reloadObs
                .startWith(Unit)
                .switchMap {
                    loaderUC.buildObservable()
                            .map { TextLoaded(it) as TestContract.StateEvent }
                            .startWith(TestContract.StateEvent.LoadingData)
                }
                .onErrorReturnItem(TextError(error = "Ops!"))
    }
}