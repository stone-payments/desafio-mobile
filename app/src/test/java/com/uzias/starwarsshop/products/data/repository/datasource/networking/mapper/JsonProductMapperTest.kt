package com.uzias.starwarsshop.products.data.repository.datasource.networking.mapper

import com.uzias.starwarsshop.products.data.repository.datasource.networking.json.JsonProduct
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class JsonProductMapperTest {

    val fakeProduct = JsonProduct(title = "Teste 1", thumbnailHd = "www.google.com/image", price = 79.00, seller = "Teste Seller")

    @Test
    fun correctly_transform_json_to_domain() {
        val domain = JsonProductMapper.transformTo(fakeProduct)
        Assert.assertEquals(fakeProduct.title, domain.title)
        Assert.assertEquals(fakeProduct.thumbnailHd, domain.pictureUrl)
        Assert.assertEquals(fakeProduct.price, domain.price)
        Assert.assertEquals(fakeProduct.seller, domain.seller)
    }

}