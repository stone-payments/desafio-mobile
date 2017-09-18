package io.hasegawa.presentation.base

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject


abstract class BasePresenter<ViewT : BaseMvpView<ViewStateT>, ViewStateT, StateEventsT>
    : MviBasePresenter<ViewT, ViewStateT>() {

    private var navigationDisposable: Disposable? = null
    private val viewStateSubject = PublishSubject.create<ViewStateT>()

    abstract fun initialViewState(): ViewStateT

    /**
     * Use this function to handle all navigation intents.
     * The stream should never end, nor emit any item.
     */
    open fun handleNavigationIntents(): Completable = Completable.complete()

    /**
     * Use this function to handle all view intents that are not for navigation.
     * The stream should never end.
     */
    abstract fun bindViewIntents(): Observable<StateEventsT>

    /**
     * This is a reducer. It takes the current view state and apply a state event, creating
     * a new view state or null. If null is returned, then the new state will be ignored. If not,
     * then the state will be updated.
     */
    abstract operator fun ViewStateT.plus(event: StateEventsT): ViewStateT?

    fun observeViewStates(): Observable<ViewStateT> = viewStateSubject

    override fun bindIntents() {
        subscribeViewState(stateObservable()) { view, state -> view.render(state) }

        navigationDisposable = handleNavigationIntents()
                .onErrorComplete()
                .subscribe()
    }

    override fun unbindIntents() {
        super.unbindIntents()

        navigationDisposable?.dispose()
        navigationDisposable = null
    }

    private fun stateObservable(): Observable<ViewStateT> {
        val initialState = initialViewState()
        return bindViewIntents()
                .scan(initialState) { state, event -> (state + event) ?: state }
                .startWith(initialState)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { viewStateSubject.onNext(it) }
    }

}