package com.uzias.starwarsshop.transactions.data.datasource.networking

import com.uzias.starwarsshop.transactions.data.datasource.networking.json.JsonTransaction
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface TransactionsApi {

    @POST("transaction")
    fun registerTransaction(@Body jsonTransaction: JsonTransaction): Flowable<JsonTransaction>

}