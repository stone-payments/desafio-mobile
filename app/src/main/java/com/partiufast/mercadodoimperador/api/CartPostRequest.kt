package com.partiufast.mercadodoimperador.api

import com.github.kittinunf.fuel.httpPost

 class CartPostRequest() {

    fun run(card_number: String, value: Int, card_holder_name: String, cvv: Int, exp_date: String): Int {
        val timeout = 5000 // 5000 milliseconds = 5 seconds.
        val parameters = listOf("card_number" to card_number,
                "value" to value,
                "cvv" to cvv,
                "card_holder_name" to card_holder_name,
                "exp_date" to exp_date)
        val (_, response, _) = "http://private-c7b10-mercadaodoimperador.apiary-mock.com/payment"
                .httpPost(parameters).timeout(timeout).responseString()
        return response.httpStatusCode
    }
}
