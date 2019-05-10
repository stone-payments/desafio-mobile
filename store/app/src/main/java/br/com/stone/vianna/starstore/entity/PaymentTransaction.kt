package br.com.stone.vianna.starstore.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class PaymentTransaction {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var cardHolderName = ""
    var cardLastDigits = ""
    var value = 0
    var transactionDateTime = ""
}