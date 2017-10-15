package personal.pedrofigueiredo.milleniumstore.data

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateTypeConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? = when(timestamp){
        null -> null
        else -> Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?) : Long?
            = date?.time

}