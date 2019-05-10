package br.com.stone.vianna.starstore.view.transactionHistory

import br.com.stone.vianna.starstore.entity.PaymentTransaction
import br.com.stone.vianna.starstore.entity.TransactionDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface TransactionRepository {

    fun getTransactionHistory(onComplete: (List<PaymentTransaction>) -> Unit)
}

class TransactionRepositoryImpl(private val transactionDao: TransactionDao)
    : TransactionRepository {


    override fun getTransactionHistory(onComplete: (List<PaymentTransaction>) -> Unit) {

        transactionDao.getTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { transactions: List<PaymentTransaction> ->
                    onComplete.invoke(transactions)
                }
    }


}