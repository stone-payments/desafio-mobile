package com.partiufast.mercadodoimperador.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.DraweeTransition
import com.partiufast.mercadodoimperador.Product
import com.partiufast.mercadodoimperador.R
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        val product = intent.getParcelableExtra<Product>(getString(R.string.arg_product_intent))
        info_drawee_view.setImageURI(product.thumbnailHd)
        if (Build.VERSION.SDK_INT >= 21) {
            window.sharedElementEnterTransition =
                    DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP,
                            ScalingUtils.ScaleType.CENTER_CROP)
        }

        product_name_label.text = product.title
        seller_label.text = "Vendedor: " + product.seller
        price_label.text = "R$ " + product.price.toString()
        button_add_cart.setOnClickListener {
            product.productCount = quantity_spinner.selectedItemPosition + 1
            //TODO: Adicionar valor 0 no Spinner
            val intent = Intent()
            intent.putExtra(getString(R.string.ADD_CART_BUTTON_EXTRA), product)
            setResult(RESULT_OK, intent)
            finish()
        }


    }

    override fun onBackPressed() {
        finish()
    }
}
