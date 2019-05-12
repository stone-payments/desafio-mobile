package br.com.stone.vianna.starstore.feature.transactionHistory

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class TransactionPresenter(val view: TransactionContract.View,
                           private val transactionRepository: TransactionRepository)
    : TransactionContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun init() {
        getTransactionHistory()
    }

    private fun getTransactionHistory() {

        transactionRepository.getTransactionHistory()
                .subscribe {
                    view.updateTransactionHistory(it)
                }
                .addTo(compositeDisposable)
    }

    override fun clearEvents() {
        compositeDisposable.clear()
    }
}