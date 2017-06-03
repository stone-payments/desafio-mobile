package com.partiufast.mercadodoimperador

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Handler
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.json.JSONArray
import org.json.JSONObject

public class CartPostRequest() {

    fun run(card_number: String, value: Int, card_holder_name: String, cvv: Int, exp_date: String): Int {
        val timeout = 5000 // 5000 milliseconds = 5 seconds.
        val readTimeout = 60000 // 60000 milliseconds = 1 minute.
        val parameters = listOf("card_number" to card_number,
                "value" to value,
                "cvv" to cvv,
                "card_holder_name" to card_holder_name,
                "exp_date" to exp_date)
        val (request, response, result) = "http://private-c7b10-mercadaodoimperador.apiary-mock.com/payment"
                .httpPost(parameters).timeout(timeout).responseString()
        return response.httpStatusCode
    }
}
