package com.example.pharol.temminstore.util

import android.arch.persistence.room.TypeConverter
import java.util.*


class TimeStampConverter{

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return (date?.time)?.toLong()
    }
}