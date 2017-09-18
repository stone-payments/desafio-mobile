package io.hasegawa.presentation.screen_listing

import io.hasegawa.presentation.screen_listing.ListingContract.ViewState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

class ListingViewRobot(val presenter: ListingPresenter) {
    val renderEvents = ReplaySubject.create<ViewState>()

    private val goToCartSubject = PublishSubject.create<Unit>()
    private val goToTransactionsSubject = PublishSubject.create<Unit>()
    private val addToCartSubject = PublishSubject.create<ListingContract.Product>()
    private val filterChangeSubject = PublishSubject.create<String>()

    val view = object : ListingContract.View {
        override fun render(state: ViewState) = renderEvents.onNext(state)

        override fun goToCartScreen(): Observable<Unit> = goToCartSubject
        override fun goToTransactionsScreen(): Observable<Unit> = goToTransactionsSubject
        override fun addToCart(): Observable<ListingContract.Product> = addToCartSubject
        override fun changedFilter(): Observable<String> = filterChangeSubject
    }

    init {
        presenter.attachView(this.view)
    }

    fun fireGoToCartIntent() = goToCartSubject.onNext(Unit)
    fun fireGoToTransactionsIntent() = goToTransactionsSubject.onNext(Unit)
    fun fireAddToCartIntent(product: ListingContract.Product) = addToCartSubject.onNext(product)
    fun fireFilterChangedIntent(filter: String) = filterChangeSubject.onNext(filter)
}