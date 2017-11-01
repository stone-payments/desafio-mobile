package com.uzias.starwarsshop.products.domain.repository

import com.uzias.starwarsshop.products.domain.model.Product
import io.reactivex.Flowable

interface ProductsRepository {
    fun getProducts() : Flowable<List<Product>>
}