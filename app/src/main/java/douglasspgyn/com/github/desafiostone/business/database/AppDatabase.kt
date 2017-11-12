package douglasspgyn.com.github.desafiostone.business.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import douglasspgyn.com.github.desafiostone.business.dao.ProductDao
import douglasspgyn.com.github.desafiostone.business.model.Product

/**
 * Created by Douglas on 12/11/17.
 */

@Database(entities = arrayOf(Product::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}