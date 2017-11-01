package com.uzias.starwarsshop.products.data.repository.datasource.networking.request

import com.google.gson.reflect.TypeToken
import com.uzias.starwarsshop.core.networking.JsonObjectConverter
import com.uzias.starwarsshop.core.networking.MockRestApi
import com.uzias.starwarsshop.core.util.fileFromResource
import com.uzias.starwarsshop.products.data.repository.datasource.networking.ProductsApi
import com.uzias.starwarsshop.products.data.repository.datasource.networking.json.JsonProduct
import org.junit.Test
import javax.inject.Inject

class GetProducts : MockRestApi<ProductsApi>() {
    override val resource: String = "rest/getProducts.json"

    private lateinit var list: List<JsonProduct>

    @Inject lateinit var restApi: ProductsApi

    override fun setUp() {
        super.setUp()

        testAppComponent.inject(this)
        val productsString = fileFromResource(resource, javaClass)
        list = JsonObjectConverter.convertArrayFromJson(productsString,
                object : TypeToken<List<JsonProduct>>() {}.type)
    }

    @Test
    fun check_get_products_response_success() {
        restApi.getProducts()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(list)
    }
}
