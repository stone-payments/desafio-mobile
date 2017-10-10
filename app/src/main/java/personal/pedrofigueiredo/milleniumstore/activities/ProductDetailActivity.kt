package personal.pedrofigueiredo.milleniumstore.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.selector
import personal.pedrofigueiredo.milleniumstore.R
import personal.pedrofigueiredo.milleniumstore.common.GlobalApplication
import personal.pedrofigueiredo.milleniumstore.data.Product
import personal.pedrofigueiredo.milleniumstore.data.ShoppingCart

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val b: Bundle = intent.extras
        val prod: Product = b.getParcelable<Product>(BUNDLE_PRODUCT)

        txtProdTitle.text = prod.title
        txtProdPrice.text = prod.price.toString()
        txtProdSeller.text = prod.seller

        Picasso.with(this).load(prod.thumb).into(productDetailedImg)

        detailCartBtn.setOnClickListener {
            val app = application as GlobalApplication
            val cart: ShoppingCart? = app.getShoppingCart()
            val qtyOptions = listOf("1", "2", "3", "4", "5")
            selector(getString(R.string.cart_quantity_selector_title), qtyOptions,
                    { _, i ->
                        cart?.addToCart(prod, qtyOptions[i].toInt())
                        longToast("Cart contents: $cart!")
                    })

        }
    }

    companion object ProductIntent {
        private val BUNDLE_PRODUCT = "product_ref"

        fun newIntent(context: Context, product: Product): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_PRODUCT, product)
            intent.putExtras(bundle)
            return intent
        }
    }
}
