package douglasspgyn.com.github.desafiostone.ui.cart

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import douglasspgyn.com.github.desafiostone.R
import douglasspgyn.com.github.desafiostone.business.model.Product
import douglasspgyn.com.github.desafiostone.common.extensions.snackbar
import douglasspgyn.com.github.desafiostone.ui.cart.adapter.CartAdapter
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity(), CartContract.View {

    private val presenter = CartPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.getCartProducts()
    }

    override fun cartLoaded(products: List<Product>) {
        cartRecycler.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = CartAdapter(products)
        }

        presenter.calculateTotalProduct()
    }

    override fun cartEmpty() {
        presenter.calculateTotalProduct()
    }

    override fun cartFailed() {
        snackbar(getString(R.string.failed_load_cart))
        presenter.calculateTotalProduct()
    }

    override fun updateTotalProduct(total: String) {
        checkoutTotal.text = getString(R.string.total_price, total)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
