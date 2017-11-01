package com.uzias.starwarsshop.transactions.data.datasource.networking

import com.uzias.starwarsshop.transactions.data.datasource.networking.mapper.JsonTransactionMapper
import com.uzias.starwarsshop.transactions.domain.model.CreditCard
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import io.reactivex.Flowable

interface TransactionsNetworkingDatasource {

    fun registerTransaction(creditCard: CreditCard, totalPrice: Double) : Flowable<Transaction>

}

class TransactionsNetworkingDatasourceImpl(private val transactionsApi: TransactionsApi): TransactionsNetworkingDatasource {

    override fun registerTransaction(creditCard: CreditCard, totalPrice: Double): Flowable<Transaction> {
        val json = JsonTransactionMapper.transformFrom(creditCard, totalPrice)
        return transactionsApi.registerTransaction(json).map{JsonTransactionMapper.transformTo(json)}
    }

}