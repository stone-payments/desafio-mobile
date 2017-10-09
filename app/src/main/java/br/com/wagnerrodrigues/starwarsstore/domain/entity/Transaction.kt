package br.com.wagnerrodrigues.starwarsstore.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "transaction")
class Transaction {

    @DatabaseField(generatedId = true)
    var id : Long? = null

    @SerializedName("card_number")
    @Expose
    var cardNumber : String? = null

    @SerializedName("value")
    @Expose
    @DatabaseField
    var value : String? = null

    @SerializedName("cvv")
    @Expose
    var cvv : String? = null

    @SerializedName("card_holder_name")
    @DatabaseField
    @Expose
    var cardHolderName : String? = null

    @SerializedName("exp_date")
    @Expose
    var expDate : String? = null

    @DatabaseField
    var lastDigits : String? = null

    @DatabaseField
    var dateTime : Date? = null
}