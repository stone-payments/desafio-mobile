package douglasspgyn.com.github.desafiostone.business.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import douglasspgyn.com.github.desafiostone.business.dao.OrderDao
import douglasspgyn.com.github.desafiostone.business.dao.ProductDao
import douglasspgyn.com.github.desafiostone.business.model.Order
import douglasspgyn.com.github.desafiostone.business.model.Product

/**
 * Created by Douglas on 12/11/17.
 */

@Database(entities = arrayOf(Product::class, Order::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
}