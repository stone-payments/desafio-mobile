package br.com.stone.vianna.starstore.feature.card

import br.com.stone.vianna.starstore.entity.*
import br.com.stone.vianna.starstore.helper.addSchedulers
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface PaymentRepository {

    fun checkout(paymentRequest: PaymentRequest,
                 onSuccess: ((PaymentTransaction) -> Unit)? = null,
                 onError: ((error: String) -> Unit)? = null)

    fun saveTransactionLocally(transaction: PaymentTransaction, onComplete: () -> Unit)
}

class PaymentRepositoryImpl(private val paymentDataSource: PaymentDataSource,
                            private val transactionDao: TransactionDao,
                            private val itemDao: ItemDao)
    : PaymentRepository {


    override fun checkout(paymentRequest: PaymentRequest,
                          onSuccess: ((PaymentTransaction) -> Unit)?,
                          onError: ((error: String) -> Unit)?) {

        paymentDataSource
                .checkout(paymentRequest)
                .addSchedulers()
                .subscribe({
                    onSuccess?.invoke(it)
                }, {
                    onError?.invoke(it.localizedMessage)
                })
    }

    override fun saveTransactionLocally(transaction: PaymentTransaction, onComplete: () -> Unit){
        Completable
                .fromAction { transactionDao.insertTransaction(transaction) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onComplete.invoke()},
                        {  })
    }

}
