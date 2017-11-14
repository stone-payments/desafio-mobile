package douglasspgyn.com.github.desafiostone.ui.product

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import douglasspgyn.com.github.desafiostone.R
import douglasspgyn.com.github.desafiostone.application.Constants
import douglasspgyn.com.github.desafiostone.business.model.Product
import douglasspgyn.com.github.desafiostone.common.extensions.loadUrl
import douglasspgyn.com.github.desafiostone.common.extensions.snackbar
import douglasspgyn.com.github.desafiostone.common.extensions.toCurrency
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity(), ProductContract.View {

    private val presenter = ProductPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.product = intent.extras.getSerializable(Constants.PRODUCT) as Product

        setListener()
        populateView()
    }

    private fun setListener() {
        addToCart.setOnClickListener {
            presenter.addToCart()
        }
    }

    override fun populateView() {
        productPhoto.loadUrl(presenter.product.thumbnailHd, R.drawable.ic_darth_vader)
        productTitle.text = presenter.product.title
        productPrice.text = presenter.product.price.toCurrency()
        productSeller.text = getString(R.string.sold_by, presenter.product.seller)
    }

    override fun productAddedToCart() {
        snackbar(getString(R.string.product_added_to_cart), view = coordinator)
    }

    override fun productFailedToCart() {
        snackbar(getString(R.string.product_failed_to_cart), view = coordinator)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
