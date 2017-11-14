package douglasspgyn.com.github.desafiostone.ui.order

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import douglasspgyn.com.github.desafiostone.R
import douglasspgyn.com.github.desafiostone.business.model.Order
import douglasspgyn.com.github.desafiostone.common.extensions.gone
import douglasspgyn.com.github.desafiostone.common.extensions.snackbar
import douglasspgyn.com.github.desafiostone.common.extensions.visible
import douglasspgyn.com.github.desafiostone.ui.order.adapter.OrderAdapter
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity(), OrderContract.View {

    val presenter = OrderPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        presenter.loadOrders()
    }

    override fun ordersLoaded(orders: List<Order>) {
        orderRecycler.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = OrderAdapter(orders)
        }

        orderRecycler.visible()
        orderEmptyContainer.gone()
    }

    override fun ordersEmpty() {
        orderRecycler.gone()
        orderEmptyContainer.visible()
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
