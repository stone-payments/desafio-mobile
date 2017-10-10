package br.com.wagnerrodrigues.starwarsstore.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PaymentResponse {

    @SerializedName("message")
    @Expose
    var message : String? = null

    @SerializedName("status")
    @Expose
    var status : String? = null

}