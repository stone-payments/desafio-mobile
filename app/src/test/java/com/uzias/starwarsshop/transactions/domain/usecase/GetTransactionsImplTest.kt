package com.uzias.starwarsshop.transactions.domain.usecase

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.uzias.starwarsshop.products.domain.model.Product
import com.uzias.starwarsshop.products.domain.repository.ProductsRepository
import com.uzias.starwarsshop.products.domain.usecase.GetProductsImpl
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import com.uzias.starwarsshop.transactions.domain.repository.TransactionsRepository
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTransactionsImplTest {
    private var repository: TransactionsRepository = mock()
    private val transactions = mutableListOf(Transaction())

    @InjectMocks
    private lateinit var usecase: GetTransactionsImpl

    @Before
    fun setUp() {
        given(repository.getTransactions()).willReturn(Flowable.just(transactions))
    }

    @Test
    fun successful_execute() {
        usecase.run()
                .test()
                .assertValue(transactions)
                .assertComplete()
                .assertNoErrors()
    }
}