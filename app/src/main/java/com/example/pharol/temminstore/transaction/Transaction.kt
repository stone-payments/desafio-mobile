package com.example.pharol.temminstore.transaction

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.pharol.temminstore.util.BigDecimalConverter
import com.example.pharol.temminstore.util.TimeStampConverter
import java.math.BigDecimal
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(@PrimaryKey(autoGenerate = true) val id: Int,
                       var card_number: String = "",
                       @TypeConverters(BigDecimalConverter::class) val value: BigDecimal = BigDecimal.ZERO,
                       val cvv:String = "",
                       val card_holder_name: String = "",
                       @TypeConverters(TimeStampConverter::class) val date: Date = Date())