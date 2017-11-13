package douglasspgyn.com.github.desafiostone.ui.cart

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
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
    }

    override fun onResume() {
        super.onResume()

        presenter.getCartProducts()
    }

    override fun cartLoaded(products: List<Product>) {
        cartRecycler.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = CartAdapter(products.toMutableList(), presenter)
        }

        presenter.calculateTotalProduct()
    }

    override fun cartEmpty() {
        cartRecycler.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = null
        }
        presenter.calculateTotalProduct()
    }

    override fun cartFailed() {
        snackbar(getString(R.string.failed_load_cart))
        presenter.calculateTotalProduct()
    }

    override fun cartCleared() {
        presenter.getCartProducts()
    }

    override fun updateTotalProduct(total: String) {
        checkoutTotal.text = getString(R.string.total_price, total)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        } else if (item?.itemId == R.id.menu_clear_cart) {
            clearCartAlert()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun clearCartAlert() {
        if (cartRecycler.adapter != null && cartRecycler.adapter.itemCount > 0) {
            AlertDialog.Builder(this).create().apply {
                setTitle(getString(R.string.clear_cart))
                setMessage(getString(R.string.clear_cart_message))
                setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.clear_cart), { _, _ ->
                    presenter.clearCart()
                })
                setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), { _, _ ->
                    dismiss()

                })
                show()
            }
        } else {
            snackbar(getString(R.string.empty_already_cart))
        }
    }
}
