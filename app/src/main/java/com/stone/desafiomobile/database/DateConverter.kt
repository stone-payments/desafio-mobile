package com.tcc.mensageria.database

import android.arch.persistence.room.TypeConverter
import java.util.*


/**
 * Classe que converte Date para Long e vice-versa para possibilitar salvar no banco
 */
class DateConverter {

    /**
     * Converte [Long] para [Date]
     * @param value valor a ser convertido
     * @return data como [Date]
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    /**
     * Converte [Date] para [Long]
     * @param value valor a ser convertido
     * @return data como [Long]
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return (if (date == null) null else date.getTime())?.toLong()
    }
}