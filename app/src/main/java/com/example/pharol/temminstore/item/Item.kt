package com.example.pharol.temminstore.item

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.pharol.temminstore.util.BigDecimalConverter
import java.math.BigDecimal


@Entity
data class Item(@PrimaryKey(autoGenerate = true) val id: Int,
                val title: String = "",
                @TypeConverters(BigDecimalConverter::class) var price: BigDecimal = BigDecimal.ZERO,
                val seller: String = "",
                val thumbnailHd: String = "")
