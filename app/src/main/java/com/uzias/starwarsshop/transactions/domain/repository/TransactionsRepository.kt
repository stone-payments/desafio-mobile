package com.uzias.starwarsshop.transactions.domain.repository

import com.uzias.starwarsshop.transactions.domain.model.CreditCard
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable

interface TransactionsRepository {

    fun getTransactions() : Flowable<List<Transaction>>

    fun registerTransaction(creditCard: CreditCard, totalPrice: Double) : Flowable<Transaction>

}