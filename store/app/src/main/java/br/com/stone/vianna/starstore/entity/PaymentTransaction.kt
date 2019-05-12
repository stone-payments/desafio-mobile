package br.com.stone.vianna.starstore.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PaymentTransaction(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val cardHolderName: String,
        val cardLastDigits: String,
        val value: Int,
        val transactionDateTime: String)