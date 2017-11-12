package douglasspgyn.com.github.desafiostone.application

import android.app.Application
import android.arch.persistence.room.Room
import douglasspgyn.com.github.desafiostone.business.dao.ProductDao
import douglasspgyn.com.github.desafiostone.business.database.AppDatabase

/**
 * Created by Douglas on 12/11/17.
 */

class App : Application() {

    private var database: AppDatabase? = null

    companion object {
        var productDao: ProductDao? = null
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "stone").allowMainThreadQueries().fallbackToDestructiveMigration().build()

        productDao = database?.productDao()
    }
}