package com.uzias.starwarsshop.transactions.presentation.presenter

import com.uzias.starwarsshop.core.behaviours.BehavioursCoordinator
import com.uzias.starwarsshop.core.presentation.BasePresenter
import com.uzias.starwarsshop.core.presentation.lifecycles.LifecycleStrategist
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import com.uzias.starwarsshop.transactions.domain.usecase.GetTransactions
import com.uzias.starwarsshop.transactions.presentation.mapper.PresentationTransactionMapper
import com.uzias.starwarsshop.transactions.presentation.view.TransactionsView
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy

interface TransactionsPresenter: BasePresenter {
    fun getTransactions()
}

class TransactionsPresenterImpl(private val getTransactions: GetTransactions,
                                private val coordinator: BehavioursCoordinator<Any>,
                                private val strategist: LifecycleStrategist,
                                private val view: TransactionsView,
                                private val ioScheduler: Scheduler,
                                private val uiScheduler: Scheduler): TransactionsPresenter {

    @Suppress("UNCHECKED_CAST")
    override fun getTransactions() {
        val getTransactions = getTransactions.run()
                .compose(coordinator as BehavioursCoordinator<List<Transaction>>)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe { view.hideRecyclerView() }
                .map(PresentationTransactionMapper::transformToList)
                .doOnNext { view.setList(it) }
                .subscribeBy(onError = {})
        strategist.applyStrategy(getTransactions)
    }

}