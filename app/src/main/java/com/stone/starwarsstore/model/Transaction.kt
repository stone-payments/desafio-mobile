package com.stone.starwarsstore.model

import com.google.gson.annotations.SerializedName

data class Transaction (
        @SerializedName("card_number")
        val cardNumber: String,
        val value: Long?,
        val cvv: Long,
        @SerializedName("card_holder_name")
        val cardHolderName: String,
        @SerializedName("exp_date")
        val expDate: String)
