package com.stone.starwarsstore.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.stone.starwarsstore.App
import com.stone.starwarsstore.Cart
import com.stone.starwarsstore.R
import com.stone.starwarsstore.extension.formatPrice
import com.stone.starwarsstore.extension.load
import com.stone.starwarsstore.model.Product
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    companion object {

        private val INTENT_EXTRA_PRODUCT = "product"

        fun newIntent(context: Context, product: Product?): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PRODUCT, product)
            return intent
        }
    }

    lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        product = intent.getSerializableExtra(INTENT_EXTRA_PRODUCT) as Product
        setTitle(product.title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actProdDetail_imProductImage.load(product.thumbnailHd, { request -> request.fit() } )
        actProdDetail_tvProductTitle.text = product.title
        actProdDetail_tvProductSeller.text = product.seller
        actProdDetail_tvProductZipCode.text = product.zipCode
        actProdDetail_tvProductDate.text = product.date
        actProdDetail_tvProductPrice.text = product.price?.formatPrice()
        actProdDetail_imPlus.setOnClickListener {
            actProdDetail_tvProductAmount.setText(modifyAmount(actProdDetail_tvProductAmount.text.toString(), true))
        }

        actProdDetail_imMinus.setOnClickListener {  if (actProdDetail_tvProductAmount.text.toString().toInt() > 1)
            actProdDetail_tvProductAmount.setText(modifyAmount(actProdDetail_tvProductAmount.text.toString(), false)) }

        actProdDetail_btAddCart.setOnClickListener {
            product.amount = actProdDetail_tvProductAmount.text.toString().toInt()
            Cart.addProduct(product)
            intent.putExtra("product", product)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    fun modifyAmount(amount: String, increase: Boolean)
            = if (increase) (amount.toInt() + 1).toString() else (amount.toInt() - 1).toString()

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}