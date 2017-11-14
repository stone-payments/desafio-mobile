package douglasspgyn.com.github.desafiostone.ui.order

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import douglasspgyn.com.github.desafiostone.R
import douglasspgyn.com.github.desafiostone.business.model.Order
import douglasspgyn.com.github.desafiostone.common.extensions.snackbar
import douglasspgyn.com.github.desafiostone.ui.order.adapter.OrderAdapter
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity(), OrderContract.View {

    val presenter = OrderPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.loadOrders()
    }

    override fun ordersLoaded(orders: List<Order>) {
        orderRecycler.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = OrderAdapter(orders)
        }
    }

    override fun ordersEmpty() {

    }

    override fun ordersFailed() {
        snackbar(getString(R.string.failed_load_order))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
