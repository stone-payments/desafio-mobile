package personal.pedrofigueiredo.milleniumstore.data

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.OnConflictStrategy.ROLLBACK

@Dao
interface OrderDAO {

    @Query("SELECT * FROM orders")
    fun getAllOrders(): List<Order>

    @Query("SELECT * FROM orders where order_id = :arg0")
    fun getOrderById(id: Long): Order

    @Insert(onConflict = ROLLBACK)
    fun insertOrder(order: Order)

    @Update(onConflict = REPLACE)
    fun updateOrder(order: Order)

    @Delete
    fun deleteOrder(order: Order)
}