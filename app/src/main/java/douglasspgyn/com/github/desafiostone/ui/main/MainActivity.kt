package douglasspgyn.com.github.desafiostone.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import douglasspgyn.com.github.desafiostone.R
import douglasspgyn.com.github.desafiostone.business.model.Product
import douglasspgyn.com.github.desafiostone.common.extensions.gone
import douglasspgyn.com.github.desafiostone.common.extensions.hide
import douglasspgyn.com.github.desafiostone.common.extensions.visible
import douglasspgyn.com.github.desafiostone.ui.cart.CartActivity
import douglasspgyn.com.github.desafiostone.ui.main.adapter.ProductAdapter
import douglasspgyn.com.github.desafiostone.ui.order.OrderActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.loadProducts(true)

        setListeners()
    }

    private fun setListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            presenter.loadProducts(false)
        }
    }

    override fun productsLoaded(products: List<Product>) {
        productsRecycler.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = ProductAdapter(products)
        }

        productsErrorContainer.gone()
        productsRecycler.visible()
    }

    override fun productsFailed() {
        productsRecycler.gone()
        productsErrorContainer.visible()
    }

    override fun showLoading() {
        loading.visible()
    }

    override fun hideLoading() {
        loading.gone()
    }

    override fun hideSwipeRefresh() {
        swipeRefreshLayout.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_cart) {
            startActivity(Intent(this, CartActivity::class.java))
        } else if (item?.itemId == R.id.menu_order) {
            startActivity(Intent(this, OrderActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }
}
