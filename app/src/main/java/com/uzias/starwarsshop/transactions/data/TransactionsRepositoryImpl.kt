package com.uzias.starwarsshop.transactions.data

import com.uzias.starwarsshop.transactions.data.datasource.database.TransactionsDatabase
import com.uzias.starwarsshop.transactions.data.datasource.networking.TransactionsNetworkingDatasource
import com.uzias.starwarsshop.transactions.domain.model.CreditCard
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import com.uzias.starwarsshop.transactions.domain.repository.TransactionsRepository
import io.reactivex.Flowable

class TransactionsRepositoryImpl(private val database: TransactionsDatabase,
                                 private val networking: TransactionsNetworkingDatasource) : TransactionsRepository {


    override fun registerTransaction(creditCard: CreditCard, totalPrice: Double)
            : Flowable<Transaction>  = networking.registerTransaction(creditCard, totalPrice)
            .flatMap{ database.addTransaction(it) }

    override fun getTransactions(): Flowable<List<Transaction>> = database.getTransactions()

}