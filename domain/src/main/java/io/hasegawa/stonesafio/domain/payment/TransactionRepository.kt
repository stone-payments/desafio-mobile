package io.hasegawa.stonesafio.domain.payment

import io.reactivex.Completable
import io.reactivex.Observable


interface TransactionRepository {
    fun insert(transaction: TransactionModel): Completable
    fun getTransactions(): Observable<List<TransactionModel>>
}
