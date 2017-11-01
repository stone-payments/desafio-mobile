package com.uzias.starwarsshop.transactions.domain.usecase

import com.uzias.starwarsshop.core.domain.UseCase
import com.uzias.starwarsshop.transactions.domain.model.CreditCard
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import com.uzias.starwarsshop.transactions.domain.repository.TransactionsRepository
import io.reactivex.Flowable

interface RegisterTransaction: UseCase<Flowable<Transaction>> {
    fun setField(creditCard: CreditCard, totalPrice: Double) : RegisterTransaction
}

class RegisterTransactionImpl(private val repository: TransactionsRepository): RegisterTransaction {

    private lateinit var creditCard: CreditCard
    private var totalPrice: Double = 0.0

    override fun setField(creditCard: CreditCard, totalPrice: Double): RegisterTransaction {
        this.creditCard = creditCard
        this.totalPrice = totalPrice
        return this
    }
    override fun run(): Flowable<Transaction> = repository.registerTransaction(creditCard, totalPrice)

}