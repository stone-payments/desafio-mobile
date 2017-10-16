package personal.pedrofigueiredo.milleniumstore.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "orders")
data class Order(
        @ColumnInfo(name="value") var value: Int = 0,
        @ColumnInfo(name="card_holder_name") var cardHolder: String = "",
        @ColumnInfo(name="last_four_digits") var lastFourDigits: String = "",
        @ColumnInfo(name="datetime_processed") var dtProcessed: Date = Date(),
        @ColumnInfo(name="order_id") @PrimaryKey(autoGenerate = true) var orderId: Long = 0
)