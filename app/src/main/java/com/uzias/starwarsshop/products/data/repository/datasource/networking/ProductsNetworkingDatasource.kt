package com.uzias.starwarsshop.products.data.repository.datasource.networking

import com.uzias.starwarsshop.products.data.repository.datasource.networking.mapper.JsonProductMapper
import com.uzias.starwarsshop.products.domain.model.Product
import io.reactivex.Flowable

interface ProductsNetworkingDatasource {
    fun getProducts(): Flowable<List<Product>>
}

class ProductsNetworkingDatasourceImpl(private val productsApi: ProductsApi): ProductsNetworkingDatasource {

    override fun getProducts(): Flowable<List<Product>> = productsApi.getProducts()
            .map(JsonProductMapper::transformToList)

}