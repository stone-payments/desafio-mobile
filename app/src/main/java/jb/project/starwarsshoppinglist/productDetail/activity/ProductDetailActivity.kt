package jb.project.starwarsshoppinglist.productDetail.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import jb.project.starwarsshoppinglist.BaseActivity
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.Utils.BadgeDrawable
import jb.project.starwarsshoppinglist.cart.activity.CartnCheckoutActivity
import jb.project.starwarsshoppinglist.model.Product
import jb.project.starwarsshoppinglist.productDetail.activity.view.ProductDetailView
import jb.project.starwarsshoppinglist.productDetail.presenter.ProductDetailPresenter
import jb.project.starwarsshoppinglist.productDetail.presenter.ProductDetailPresenterImpl
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.content_product_detail.*


class ProductDetailActivity : BaseActivity(), ProductDetailView {

    private lateinit var mIcon: LayerDrawable
    private lateinit var mProduct: Product
    private lateinit var mPresenter: ProductDetailPresenter
    private lateinit var badge: BadgeDrawable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setSupportActionBar(toolbar)

        mPresenter = ProductDetailPresenterImpl()
        mPresenter.init(this)
        badge = BadgeDrawable(this)

        loadInitialView()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        button_add_cart.setOnClickListener { addCartClick() }

    }

    @SuppressLint("SetTextI18n")
    private fun loadInitialView() {
        mProduct = intent.getParcelableExtra("product")

        textView_name.text = mProduct.title
        textView_value.text = "R$ " + String.format("%.2f", mProduct.price?.div(100.0))

        textView_seller.text = mProduct.seller
        Picasso.with(this).load(mProduct.thumbnailHd).placeholder(R.drawable.img_not_available).into(imageView)
        spinner_quantity.adapter = ArrayAdapter<Int>(this, R.layout.support_simple_spinner_dropdown_item, mutableListOf(1, 2, 3, 4, 5))

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        if (item.itemId == R.id.action_cart) {
            openCheckoutActivity()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun addCartClick() {
        val cart = mProduct.copy()
        cart.quantity = spinner_quantity.selectedItem as Int
        mPresenter.addCart(cart)
        mCountCart = mPresenter.getCountCart()
        badge.setBadgeCount(this, mIcon, mCountCart)
        openCheckoutActivity()

    }

    private fun openCheckoutActivity() {
        val mIntent = Intent(this, CartnCheckoutActivity::class.java)
        this.startActivity(mIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        val itemCart = menu.findItem(R.id.action_cart)
        mIcon = itemCart.icon as LayerDrawable
        mCountCart = mPresenter.getCountCart()
        badge.setBadgeCount(this, mIcon, mCountCart)
        return true
    }


    override fun onDestroy() {
        mPresenter.destroy()
        super.onDestroy()
    }
}

