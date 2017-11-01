package com.uzias.starwarsshop.transactions.data.datasource.networking.json

import com.google.gson.annotations.SerializedName
import com.uzias.starwarsshop.core.domain.InvalidData

data class JsonTransaction(val value: Double = InvalidData.UNINITIALIZED.getDouble(),
                       @SerializedName("card_number") val cardNumber: String = InvalidData.UNINITIALIZED.getString(),
                       @SerializedName("card_holder_name") val name: String = InvalidData.UNINITIALIZED.getString(),
                       @SerializedName("exp_date") val expiry: String = InvalidData.UNINITIALIZED.getString())