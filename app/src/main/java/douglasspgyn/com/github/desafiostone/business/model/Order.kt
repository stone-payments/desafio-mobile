package douglasspgyn.com.github.desafiostone.business.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Douglas on 13/11/17.
 */

@Entity(tableName = "orders")
data class Order(var value: Int = 0,
                 var date: Long = 0,
                 var cardNumber: String = "",
                 var cardHolderName: String = "",
                 @PrimaryKey(autoGenerate = true) var id: Int = 0)
