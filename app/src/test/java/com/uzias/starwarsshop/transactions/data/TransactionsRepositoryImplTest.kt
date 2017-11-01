package com.uzias.starwarsshop.transactions.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.uzias.starwarsshop.transactions.data.datasource.database.TransactionsDatabase
import com.uzias.starwarsshop.transactions.data.datasource.networking.TransactionsNetworkingDatasource
import com.uzias.starwarsshop.transactions.domain.model.CreditCard
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TransactionsRepositoryImplTest {

    private val networking: TransactionsNetworkingDatasource = mock()
    private val database: TransactionsDatabase = mock()
    private val transaction = Transaction()
    private val transactions = mutableListOf(transaction)

    @InjectMocks
    private lateinit var repository: TransactionsRepositoryImpl

    @Before
    fun setUp() {
        given(database.getTransactions()).willReturn(Flowable.just(transactions))
        given(networking.registerTransaction(any(), any())).willReturn(Flowable.just(transaction))
        given(database.addTransaction(any())).willReturn(Flowable.just(transaction))
    }

    @Test
    fun must_call_get_transactions() {
        repository.getTransactions()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(transactions)

        verify(database).getTransactions()
    }

    @Test
    fun must_call_register_transaction() {
        val creditCard = CreditCard()
        val total = 1.0
        repository.registerTransaction(CreditCard(), total)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(transaction)

        verify(database).addTransaction(transaction)
        verify(networking).registerTransaction(creditCard, total)
    }

}