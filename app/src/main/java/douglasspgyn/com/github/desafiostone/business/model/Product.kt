package douglasspgyn.com.github.desafiostone.business.model

import java.io.Serializable


/**
 * Created by Douglas on 12/11/17.
 */
data class Product(val title: String = "",
                   val price: Double = 0.0,
                   val zipcode: String = "",
                   val seller: String = "",
                   val thumbnailHd: String = "",
                   val date: String = "") : Serializable