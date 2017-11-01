package com.uzias.starwarsshop.transactions.domain.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.uzias.starwarsshop.transactions.domain.model.CreditCard
import com.uzias.starwarsshop.transactions.domain.model.Transaction
import com.uzias.starwarsshop.transactions.domain.repository.TransactionsRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterTransactionImplTest {
    private var repository: TransactionsRepository = mock()
    private val transaction = Transaction()

    @InjectMocks
    private lateinit var usecase: RegisterTransactionImpl

    @Before
    fun setUp() {
        given(repository.registerTransaction(any(), any())).willReturn(Flowable.just(transaction))
    }

    @Test
    fun successful_execute() {
        usecase.setField(CreditCard(), 0.0)
                .run()
                .test()
                .assertValue(transaction)
                .assertComplete()
                .assertNoErrors()
    }
}