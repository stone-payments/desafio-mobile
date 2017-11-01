package com.uzias.starwarsshop.transactions.data.datasource.networking.request

import com.uzias.starwarsshop.core.networking.JsonObjectConverter
import com.uzias.starwarsshop.core.networking.MockRestApi
import com.uzias.starwarsshop.core.util.fileFromResource
import com.uzias.starwarsshop.transactions.data.datasource.networking.TransactionsApi
import com.uzias.starwarsshop.transactions.data.datasource.networking.json.JsonTransaction
import org.junit.Test
import javax.inject.Inject

class AddTransaction : MockRestApi<TransactionsApi>() {
    override val resource: String = "rest/AddTransaction.json"

    private lateinit var transaction: JsonTransaction

    @Inject lateinit var restApi: TransactionsApi

    override fun setUp() {
        super.setUp()

        testAppComponent.inject(this)
        val transactionsString = fileFromResource(resource, javaClass)
        transaction = JsonObjectConverter.convertFromJson(transactionsString, JsonTransaction::class.java)
    }

    @Test
    fun check_get_products_response_success() {
        restApi.registerTransaction(JsonTransaction())
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(transaction)
    }
}