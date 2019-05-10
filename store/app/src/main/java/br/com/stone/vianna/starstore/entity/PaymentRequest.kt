package br.com.stone.cryptowallet.entity

import com.google.gson.annotations.SerializedName

class PaymentRequest {

    @SerializedName("card_number")
    var cardNumber = ""
    @SerializedName("exp_date")
    var expirationDate = ""
    @SerializedName("cvv")
    var securityCode = ""
    @SerializedName("card_holder_name")
    var cardHolder = ""
    @SerializedName("value")
    var value = 0
}