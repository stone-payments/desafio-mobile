package personal.pedrofigueiredo.milleniumstore.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.data.Product

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val productTitle = intent.getStringExtra(INTENT_PRODUCT_TITLE)
        val productPrice = intent.getStringExtra(INTENT_PRODUCT_PRICE)
        val productSeller = intent.getStringExtra(INTENT_PRODUCT_SELLER)
        val productThumb = intent.getStringExtra(INTENT_PRODUCT_THUMB)

        txtProdTitle.text = productTitle
        txtProdPrice.text = productPrice
        txtProdSeller.text = productSeller

        Picasso.with(this).load(productThumb).into(productDetailedImg)

    }

    companion object ProductIntent {
        private val INTENT_PRODUCT_TITLE = "product_title"
        private val INTENT_PRODUCT_SELLER = "product_seller"
        private val INTENT_PRODUCT_PRICE = "product_price"
        private val INTENT_PRODUCT_THUMB = "product_thumbnail"

        fun newIntent(context: Context, product: Product): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(INTENT_PRODUCT_TITLE, product.title)
            intent.putExtra(INTENT_PRODUCT_PRICE, product.price)
            intent.putExtra(INTENT_PRODUCT_SELLER, product.seller)
            intent.putExtra(INTENT_PRODUCT_THUMB, product.thumb)
            return intent
        }
    }
}
