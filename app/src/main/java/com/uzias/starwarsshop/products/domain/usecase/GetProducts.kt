package com.uzias.starwarsshop.products.domain.usecase

import com.uzias.starwarsshop.core.domain.UseCase
import com.uzias.starwarsshop.products.domain.model.Product
import com.uzias.starwarsshop.products.domain.repository.ProductsRepository
import io.reactivex.Flowable

interface GetProducts: UseCase<Flowable<List<Product>>>

class GetProductsImpl(private val productsRepository: ProductsRepository): GetProducts {

    override fun run(): Flowable<List<Product>> = productsRepository.getProducts()

}