package douglasspgyn.com.github.desafiostone.business.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Douglas on 13/11/17.
 */

data class OrderRequest(@SerializedName("card_number") val cardNumber: String = "",
                        val value: Int = 0,
                        val cvv: Int = 0,
                        @SerializedName("card_holder_name") val cardHolderName: String = "",
                        @SerializedName("exp_date") val expiresDate: String = "")
