package com.stone.desafiomobile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.stone.desafiomobile.database.AppDatabase
import com.stone.desafiomobile.database.ProductDAO
import com.stone.desafiomobile.model.Product
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class DbTests {
    private lateinit var productDAO: ProductDAO
    private lateinit var mDb: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        productDAO = mDb.getProductDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb.close()
    }

    @Test
    fun testProductInsert() {
        val product = Product(1, "title", 10f, "seller", "thumb")
        var values = productDAO.insert(product)

        Assert.assertTrue("nenhum valor inserido no banco", values.isNotEmpty())
        values = productDAO.insert(product)
        Assert.assertTrue("Valor repetido inserido", values.size == 1)

        var DBvalue = productDAO.findById(product.id!!).blockingObserve()
        Assert.assertEquals("valor diferente no banco", product, DBvalue)
    }

    fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)
        val innerObserver = Observer<T> {
            value = it
            latch.countDown()
        }
        observeForever(innerObserver)
        latch.await(2, TimeUnit.SECONDS)
        return value
    }
}


