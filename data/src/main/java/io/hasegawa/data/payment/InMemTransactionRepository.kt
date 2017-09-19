package io.hasegawa.data.payment

import io.hasegawa.stonesafio.domain.payment.TransactionModel
import io.hasegawa.stonesafio.domain.payment.TransactionRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


class InMemTransactionRepository : TransactionRepository {
    private val transactions = mutableListOf<TransactionModel>()
    private val transactionsSubj = BehaviorSubject.createDefault<List<TransactionModel>>(emptyList())

    override fun insert(transaction: TransactionModel): Completable = Completable.fromCallable {
        transactions.add(transaction)
        transactionsSubj.onNext(transactions + emptyList())
    }

    override fun getTransactions(): Observable<List<TransactionModel>> = transactionsSubj
}