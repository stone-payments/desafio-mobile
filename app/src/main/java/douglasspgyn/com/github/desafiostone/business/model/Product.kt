package douglasspgyn.com.github.desafiostone.business.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Douglas on 12/11/17.
 */

@Entity(tableName = "products")
data class Product(@PrimaryKey var title: String = "",
                   var price: Double = 0.0,
                   var zipcode: String = "",
                   var seller: String = "",
                   var thumbnailHd: String = "",
                   var date: String = "",
                   var quantity: Int = 1) : Serializable