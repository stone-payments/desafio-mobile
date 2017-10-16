package personal.pedrofigueiredo.milleniumstore.common

import android.app.Application
import android.arch.persistence.room.Room
import personal.pedrofigueiredo.milleniumstore.data.Database
import personal.pedrofigueiredo.milleniumstore.data.ShoppingCart

class GlobalApplication : Application() {
    private var sCart: ShoppingCart? = null
    private var database: Database? = null

    private val DATABASE_NAME = "MillenniumDB"

    override fun onCreate() {
        super.onCreate()
        sCart = ShoppingCart()

        database = Room.databaseBuilder(applicationContext, Database::class.java, DATABASE_NAME)
                .build()
    }

    fun getShoppingCart(): ShoppingCart? {
        return sCart
    }

    fun getDB(): Database? {
        return database
    }
}