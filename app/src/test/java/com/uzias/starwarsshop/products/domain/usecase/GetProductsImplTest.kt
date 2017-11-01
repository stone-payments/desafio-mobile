package com.uzias.starwarsshop.products.domain.usecase

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.uzias.starwarsshop.products.domain.model.Product
import com.uzias.starwarsshop.products.domain.repository.ProductsRepository
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetProductsImplTest {
    private var repository: ProductsRepository = mock()
    private val products = mutableListOf(Product())

    @InjectMocks
    private lateinit var usecase: GetProductsImpl

    @Before
    fun setUp() {
        given(repository.getProducts()).willReturn(Flowable.just(products))
    }

    @Test
    fun successful_execute() {
        usecase.run()
                .test()
                .assertValue(products)
                .assertComplete()
                .assertNoErrors()
    }
}