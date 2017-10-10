package br.com.wagnerrodrigues.starwarsstore.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

class Product {

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("price")
    @Expose
    var price: BigDecimal? = null

    var priceFormated: String? = null

    @SerializedName("zipcode")
    @Expose
    var zipcode: String? = null

    @SerializedName("seller")
    @Expose
    var seller: String? = null

    @SerializedName("thumbnailHd")
    @Expose
    var thumbnailHd: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

}