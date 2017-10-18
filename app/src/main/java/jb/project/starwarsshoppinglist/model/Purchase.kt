package jb.project.starwarsshoppinglist.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.util.*

/**
 * Created by Jb on 17/10/2017.
 */
open class Purchase(
        @SerializedName("card_number")
        var cardNumber: String? = null,
        var value: Int? = null,
        var cvv: Int? = null,
        @SerializedName("card_holder_name")
        var cardHolderName: String? = null,
        @SerializedName("exp_date")
        var expDate: String? = null,
        var datePurchased: Date = Date()
) : RealmObject()
