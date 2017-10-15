package personal.pedrofigueiredo.milleniumstore.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_order_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.adapters.OrdersListAdapter
import personal.pedrofigueiredo.milleniumstore.common.GlobalApplication
import personal.pedrofigueiredo.milleniumstore.data.Order

class OrderListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        doAsync {
            val app = application as GlobalApplication
            val db = app.getDB()
            val order = db?.orderDao()
            val items = order?.getAllOrdersRecentFirst() as ArrayList<Order>

            uiThread {
                val adapter = OrdersListAdapter(this@OrderListActivity, items)
                ordersList.adapter = adapter
            }
        }
    }
}
