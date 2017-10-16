package personal.pedrofigueiredo.milleniumstore.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(entities = arrayOf(Order::class), version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun orderDao(): OrderDAO
}