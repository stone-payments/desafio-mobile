package br.com.stone.vianna.starstore.view.transactionHistory

import br.com.stone.vianna.starstore.entity.PaymentTransaction
import br.com.stone.vianna.starstore.entity.TransactionDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TransactionPresenter(val view: TransactionContract.View,
                           private val transactionDao: TransactionDao) : TransactionContract.Presenter {

    override fun init() {
        getTransactionHistory()
    }

    private fun getTransactionHistory() {
        transactionDao.getTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { transactions: List<PaymentTransaction> -> view.updateTransactionHistory(transactions) }
    }
}