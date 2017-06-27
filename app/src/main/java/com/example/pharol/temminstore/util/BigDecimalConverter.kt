package com.example.pharol.temminstore.util

import android.arch.persistence.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter{

    @TypeConverter
    fun toBigDecimal(d: Double): BigDecimal? {
        return BigDecimal.valueOf(d)
    }

    @TypeConverter
    fun toDouble(bd : BigDecimal): Double {
        return bd.toDouble()
    }

}