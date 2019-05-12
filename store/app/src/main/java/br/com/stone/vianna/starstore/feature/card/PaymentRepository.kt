package br.com.stone.vianna.starstore.feature.card

import br.com.stone.vianna.starstore.entity.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface PaymentRepository {

    fun saveTransactionLocally(transaction: PaymentTransaction): Completable
    fun checkout(paymentRequest: PaymentRequest): Observable<PaymentTransaction>
}

class PaymentRepositoryImpl(private val paymentApi: PaymentApi,
                            private val transactionDao: TransactionDao) : PaymentRepository {


    override fun checkout(paymentRequest: PaymentRequest): Observable<PaymentTransaction> {

        return paymentApi.checkout(paymentRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveTransactionLocally(transaction: PaymentTransaction): Completable {
        return transactionDao.insertTransaction(transaction)
                .subscribeOn(Schedulers.io())
    }

}
