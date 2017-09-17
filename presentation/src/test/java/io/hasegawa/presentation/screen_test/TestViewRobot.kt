package io.hasegawa.presentation.screen_test

import io.hasegawa.presentation.screen_test.TestContract.ViewState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

class TestViewRobot(presenter: TestPresenter) {
    val renderEvents: ReplaySubject<ViewState> = ReplaySubject.create<ViewState>()

    private val reloadDataSubject = PublishSubject.create<Unit>()
    private val nextScreenSubject = PublishSubject.create<Unit>()

    private val view = object : TestContract.View {
        override fun render(state: ViewState) = renderEvents.onNext(state)
        override fun reloadData(): Observable<Unit> = reloadDataSubject
        override fun nextScreen(): Observable<Unit> = nextScreenSubject
    }

    init {
        presenter.attachView(view)
    }

    fun fireReloadDataIntent() = reloadDataSubject.onNext(Unit)
    fun fireNextScreenIntent() = nextScreenSubject.onNext(Unit)
}