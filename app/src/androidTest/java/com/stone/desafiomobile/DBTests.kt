package com.stone.desafiomobile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.stone.desafiomobile.database.AppDatabase
import com.stone.desafiomobile.database.ProductDAO
import com.stone.desafiomobile.database.PurchaseLogDAO
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.model.PurchaseLog
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Tests do banco de dados
 */
@RunWith(AndroidJUnit4::class)
class DbTests {
    private lateinit var productDAO: ProductDAO
    private lateinit var purchaseLogDAO: PurchaseLogDAO
    private lateinit var mDb: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        productDAO = mDb.getProductDAO()
        purchaseLogDAO = mDb.getPurchaseLogDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb.close()
    }

    /**
     * Testa a inserção de [Product]
     */
    @Test
    fun testProductInsert() {
        val product = Product("title", 10L, "seller", "thumb")
        var values = productDAO.insert(product)

        Assert.assertTrue("nenhum valor inserido no banco", values.isNotEmpty())
        values = productDAO.insert(product)
        Assert.assertTrue("Valor repetido inserido", values.size == 1)

        var DBvalue = productDAO.findById(product.title, product.seller).blockingObserve()
        Assert.assertEquals("valor diferente no banco", product, DBvalue)
    }

    /**
     * Testa a inserção de [PurchaseLog]
     */
    @Test
    fun testPurchaseLogInsert() {
        val purchaseLog = PurchaseLog(1L, 50L, Date(), "0123", "Renan")
        var values = purchaseLogDAO.insert(purchaseLog)

        Assert.assertTrue("nenhum valor inserido no banco", values.isNotEmpty())
        values = purchaseLogDAO.insert(purchaseLog)
        Assert.assertTrue("Valor repetido inserido", values.size == 1)

        var DBvalue = purchaseLogDAO.findById(purchaseLog.id!!).blockingObserve()
        Assert.assertEquals("valor diferente no banco", purchaseLog, DBvalue)
    }

    /**
     * faz o [LiveData] funcionar de forma sincrona
     */
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


