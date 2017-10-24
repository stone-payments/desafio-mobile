package com.stone.starwarsstore.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
        var price: Long? = null,
        var thumbnailHd: String? = null,
        @SerializedName("zipcode")
        var zipCode: String? = null,
        var date: String? = null,
        var amount: Int? = null
) : Serializable {
    lateinit var title: String
    lateinit var seller: String

    constructor(
            title: String,
            price: Long? = null,
            seller: String,
            thumbnailHd: String? = null,
            zipCode: String? = null,
            date: String? = null
    ): this(price, thumbnailHd, zipCode, date) {
        this.title = title
        this.seller = seller
        this.zipCode = zipCode
        this.date = date
    }
}