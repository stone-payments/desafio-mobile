package com.uzias.starwarsshop.products.data.repository

import com.uzias.starwarsshop.core.networking.errors.DeserializationIssuesHandler
import com.uzias.starwarsshop.core.networking.errors.NetworkingErrorHandler
import com.uzias.starwarsshop.core.networking.errors.RestErrorsHandler
import com.uzias.starwarsshop.products.data.repository.datasource.networking.ProductsNetworkingDatasource
import com.uzias.starwarsshop.products.domain.model.Product
import com.uzias.starwarsshop.products.domain.repository.ProductsRepository
import io.reactivex.Flowable

class ProductsRepositoryImpl(private val networking: ProductsNetworkingDatasource) : ProductsRepository {

    override fun getProducts(): Flowable<List<Product>> = networking.getProducts()
            .compose(NetworkingErrorHandler<List<Product>>())
            .compose(RestErrorsHandler<List<Product>>())
            .compose(DeserializationIssuesHandler<List<Product>>())

}