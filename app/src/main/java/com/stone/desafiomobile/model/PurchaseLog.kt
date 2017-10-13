package com.stone.desafiomobile.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(tableName = "purchase_logs")
data class PurchaseLog(
        @PrimaryKey var id: Long? = null,
        var value: Long? = null,
        var date: Date? = null,
        @ColumnInfo(name = "last_card_digits")
        var lastCardDigits: String? = null,
        @ColumnInfo(name = "card_holder_name")
        var cardHolderName: String? = null
) {

}