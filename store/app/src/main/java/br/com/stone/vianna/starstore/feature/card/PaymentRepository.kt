package br.com.stone.vianna.starstore.feature.card

import br.com.stone.vianna.starstore.entity.*
import br.com.stone.vianna.starstore.helper.addSchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface PaymentRepository {

    fun saveTransactionLocally(transaction: PaymentTransaction): Completable
    fun checkout(paymentRequest: PaymentRequest): Observable<PaymentTransaction>
}

class PaymentRepositoryImpl(private val paymentDataSource: PaymentDataSource,
                            private val transactionDao: TransactionDao,
                            private val itemDao: ItemDao)
    : PaymentRepository {


    override fun checkout(paymentRequest: PaymentRequest): Observable<PaymentTransaction> {

        return paymentDataSource.checkout(paymentRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveTransactionLocally(transaction: PaymentTransaction): Completable {
        return Completable
                .fromAction { transactionDao.insertTransaction(transaction) }
                .subscribeOn(Schedulers.io())
    }

}
