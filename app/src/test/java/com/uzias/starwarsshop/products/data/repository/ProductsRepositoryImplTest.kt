package com.uzias.starwarsshop.products.data.repository

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.uzias.starwarsshop.products.data.repository.datasource.networking.ProductsNetworkingDatasource
import com.uzias.starwarsshop.products.domain.model.Product
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductsRepositoryImplTest {

    private val networkingDatasource: ProductsNetworkingDatasource = mock()

    private val products = mutableListOf(Product())

    @InjectMocks
    private lateinit var repository: ProductsRepositoryImpl

    @Before
    fun setUp() {
        given(networkingDatasource.getProducts()).willReturn(Flowable.just(products))
    }

    @Test
    fun must_call_get_products() {
        repository.getProducts()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(products)

        verify(networkingDatasource).getProducts()
    }

}