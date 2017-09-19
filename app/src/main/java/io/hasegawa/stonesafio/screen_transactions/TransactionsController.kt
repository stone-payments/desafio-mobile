package io.hasegawa.stonesafio.screen_transactions

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.hasegawa.presentation.screen_transactions.TransactionsContract.StateEvent
import io.hasegawa.presentation.screen_transactions.TransactionsContract.View
import io.hasegawa.presentation.screen_transactions.TransactionsContract.ViewState
import io.hasegawa.presentation.screen_transactions.TransactionsPresenter
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.BaseController
import io.hasegawa.stonesafio.common.bindView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class TransactionsController
    : BaseController<View, ViewState, StateEvent, TransactionsPresenter>(), View {
    private val backSubj = PublishSubject.create<Unit>()

    private val recyclerView: RecyclerView by bindView(R.id.transactions_rv)
    private var rvController: TransactionsRvController? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup,
                             savedViewState: Bundle?): android.view.View =
            inflater.inflate(R.layout.screen_transactions, container, false)

    override fun onViewBound() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        rvController = TransactionsRvController()
        recyclerView.adapter = rvController!!.adapter
    }

    override fun createPresenter(): TransactionsPresenter =
            TransactionsDIComponent.initialize(this).getPresenter()

    override fun handleBack(): Boolean {
        backSubj.onNext(Unit)
        return true
    }

    override fun render(state: ViewState) {
        if (state is ViewState.Loaded) {
            rvController!!.setData(state.transactions)
        } else {
            rvController!!.setData(emptyList())
        }
    }

    override fun back(): Observable<Unit> = backSubj
}