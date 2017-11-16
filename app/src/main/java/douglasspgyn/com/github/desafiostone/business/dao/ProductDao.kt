package douglasspgyn.com.github.desafiostone.business.dao

import android.arch.persistence.room.*
import douglasspgyn.com.github.desafiostone.business.model.Product

/**
 * Created by Douglas on 12/11/17.
 */

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getProducts(): List<Product>

    @Query("SELECT * FROM products WHERE title LIKE :arg0 LIMIT 1")
    fun getProduct(title: String): Product

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("DELETE FROM products")
    fun deleteAll()
}