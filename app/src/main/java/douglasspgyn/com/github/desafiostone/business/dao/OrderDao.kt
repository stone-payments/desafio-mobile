package douglasspgyn.com.github.desafiostone.business.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import douglasspgyn.com.github.desafiostone.business.model.Order

/**
 * Created by Douglas on 13/11/17.
 */

@Dao
interface OrderDao {

    @Query("SELECT * FROM orders ORDER BY date DESC")
    fun getOrders(): List<Order>

    @Insert
    fun saveOrder(order: Order)
}