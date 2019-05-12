package br.com.stone.vianna.starstore.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class PaymentTransaction(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val cardHolderName: String,
        val cardLastDigits: String,
        val value: Int,
        val transactionDateTime: String)