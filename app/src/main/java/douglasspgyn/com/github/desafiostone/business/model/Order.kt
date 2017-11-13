package douglasspgyn.com.github.desafiostone.business.model

import android.arch.persistence.room.Entity

/**
 * Created by Douglas on 13/11/17.
 */

@Entity(tableName = "orders")
data class Order(val value: Double = 0.0,
                 val date: Long = 0,
                 val cardNumber: String = "",
                 val cardHolderName: String = "")
