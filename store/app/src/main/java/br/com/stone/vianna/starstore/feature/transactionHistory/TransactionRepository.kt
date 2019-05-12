package br.com.stone.vianna.starstore.feature.transactionHistory

import br.com.stone.vianna.starstore.entity.PaymentTransaction
import br.com.stone.vianna.starstore.entity.TransactionDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface TransactionRepository {

    fun getTransactionHistory(): Observable<List<PaymentTransaction>>
}

class TransactionRepositoryImpl(private val transactionDao: TransactionDao)
    : TransactionRepository {


    override fun getTransactionHistory(): Observable<List<PaymentTransaction>> {

        return transactionDao.getTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


}