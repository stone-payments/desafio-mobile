package com.uzias.starwarsshop.products.data.repository.datasource.networking.mapper

import com.uzias.starwarsshop.core.mapper.BaseMapper
import com.uzias.starwarsshop.products.data.repository.datasource.networking.json.JsonProduct
import com.uzias.starwarsshop.products.domain.model.Product

object JsonProductMapper : BaseMapper<JsonProduct, Product>() {

    override fun transformFrom(source: Product): JsonProduct {
        TODO("not implemented")
    }

    override fun transformTo(source: JsonProduct): Product = Product(title = source.title,
            pictureUrl = source.thumbnailHd, price = source.price, seller = source.seller)

}