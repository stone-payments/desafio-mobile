package br.com.stone.vianna.starstore.entity

import com.google.gson.annotations.SerializedName

data class PaymentRequest(
        @SerializedName("card_number")
        val cardNumber: String,
        @SerializedName("exp_date")
        val expirationDate: String,
        @SerializedName("cvv")
        val securityCode: String,
        @SerializedName("card_holder_name")
        val cardHolder: String,
        @SerializedName("value")
        val value: Int)