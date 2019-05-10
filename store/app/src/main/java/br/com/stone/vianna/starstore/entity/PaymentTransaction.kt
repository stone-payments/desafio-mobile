package br.com.stone.cryptowallet.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class PaymentTransaction {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @ColumnInfo(name = "cardHolderName")
    var cardHolderName = ""
    @ColumnInfo(name = "cardLastDigits")
    var cardLastDigits = ""
    @ColumnInfo(name = "value")
    var value = 0
    @ColumnInfo(name = "transactionDateTime")
    var transactionDateTime = ""
}