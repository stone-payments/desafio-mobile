package com.uzias.starwarsshop.transactions.domain.usecase

import com.uzias.starwarsshop.core.domain.UseCase
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import com.uzias.starwarsshop.transactions.domain.repository.TransactionsRepository
import io.reactivex.Flowable

interface GetTransactions: UseCase<Flowable<List<Transaction>>>

class GetTransactionsImpl(private val repository: TransactionsRepository): GetTransactions {

    override fun run(): Flowable<List<Transaction>> = repository.getTransactions()

}