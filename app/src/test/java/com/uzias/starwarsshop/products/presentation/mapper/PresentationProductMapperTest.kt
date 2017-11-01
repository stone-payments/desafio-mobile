package com.uzias.starwarsshop.products.presentation.mapper

import com.uzias.starwarsshop.products.domain.model.Product
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PresentationProductMapperTest {

    val fakeProduct = Product(title = "Teste 1", pictureUrl = "www.google.com/image", price = 79.00, seller = "Teste Seller")

    @Test
    fun correctly_transform_domain_to_presentation() {
        val presentation = PresentationProductMapper.transformTo(fakeProduct)
        Assert.assertEquals(fakeProduct.title, presentation.title)
        Assert.assertEquals(fakeProduct.pictureUrl, presentation.pictureUrl)
        Assert.assertEquals(fakeProduct.price, presentation.price)
        Assert.assertEquals(fakeProduct.seller, presentation.seller)
        Assert.assertEquals(0, presentation.totalItem)
    }

}