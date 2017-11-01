package com.uzias.starwarsshop.products.presentation.mapper

import com.uzias.starwarsshop.core.mapper.BaseMapper
import com.uzias.starwarsshop.products.domain.model.Product
import com.uzias.starwarsshop.products.presentation.model.PresentationProduct

object PresentationProductMapper : BaseMapper<Product, PresentationProduct>() {

    override fun transformTo(source: Product): PresentationProduct = PresentationProduct(
            title = source.title, pictureUrl = source.pictureUrl, price = source.price, seller = source.seller)

    override fun transformFrom(source: PresentationProduct): Product {
        TODO("not implemented")
    }

}