package jb.project.starwarsshoppinglist.productDetail.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.realm.Realm
import jb.project.starwarsshoppinglist.BaseActivity
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.Utils.BadgeDrawable
import jb.project.starwarsshoppinglist.model.Cart
import jb.project.starwarsshoppinglist.model.Product
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.content_product_detail.*


class ProductDetailActivity : BaseActivity() {

    private lateinit var mIcon: LayerDrawable
    private lateinit var mProduct: Product
    var mCountCart: String = "0"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setSupportActionBar(toolbar)



        mProduct = intent.getParcelableExtra("product")
        textView_name.text = mProduct.title
        textView_value.text = "R$ " + String.format("%.2f", mProduct.price?.div(100.0))

        textView_seller.text = mProduct.seller
        Picasso.with(this).load(mProduct.thumbnailHd).into(imageView, object : Callback {
            override fun onSuccess() {}
            override fun onError() {
                imageView.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.img_not_available))
            }
        })

        val realm = Realm.getDefaultInstance()
        realm.use {
            it.beginTransaction()
            val cartCount = realm
                    .where(Cart::class.java)
                    .findAll()
            mCountCart = cartCount.size.toString()
            it.close()
        }

        spinner_quantity.adapter = ArrayAdapter<Int>(this, R.layout.support_simple_spinner_dropdown_item, mutableListOf(1, 2, 3, 4, 5))


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        button_add_cart.setOnClickListener { addCartClick() }
    }

    private fun addCartClick() {
        val cart = mProduct.copy()
        cart.quantity = spinner_quantity.selectedItem as Int

        val realm = Realm.getDefaultInstance()
        realm.use {
            it.beginTransaction()
            it.copyToRealmOrUpdate(cart)
            it.commitTransaction()
            val cartCount = realm
                    .where(Cart::class.java)
                    .findAll()
            setBadgeCount(this, mIcon, cartCount.size.toString())
            it.close()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_cart, menu)
        val itemCart = menu.findItem(R.id.action_cart)
        mIcon = itemCart.icon as LayerDrawable
        setBadgeCount(this, mIcon, mCountCart)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_cart) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setBadgeCount(context: Context, icon: LayerDrawable, count: String) {

        val badge: BadgeDrawable

        // Reuse drawable if possible
        val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
        badge = if (reuse != null && reuse is BadgeDrawable) {
            reuse
        } else {
            BadgeDrawable(context)
        }

        badge.setCount(count)
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_badge, badge)
    }
}

