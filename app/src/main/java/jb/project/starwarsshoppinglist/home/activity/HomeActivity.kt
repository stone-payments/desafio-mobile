package jb.project.starwarsshoppinglist.home.activity

import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import jb.project.starwarsshoppinglist.BaseActivity
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.Utils.BadgeDrawable
import jb.project.starwarsshoppinglist.cart.activity.CartnCheckoutActivity
import jb.project.starwarsshoppinglist.home.activity.view.HomeView
import jb.project.starwarsshoppinglist.home.adapter.ProductListsAdapter
import jb.project.starwarsshoppinglist.home.presenter.HomePresenter
import jb.project.starwarsshoppinglist.home.presenter.HomePresenterImpl
import jb.project.starwarsshoppinglist.model.Product
import jb.project.starwarsshoppinglist.orderHistory.activity.OrderHistoryActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(), HomeView {

    private lateinit var mPresenter: HomePresenter
    private lateinit var mIcon: LayerDrawable
    private lateinit var badge: BadgeDrawable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mPresenter = HomePresenterImpl()
        badge = BadgeDrawable(this)

        progress_bar.visibility = View.VISIBLE
        mPresenter.init(this)

    }

    override fun loadProductList(productList: ArrayList<Product>) {
        progress_bar.visibility = View.GONE

        recyler_product_items.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyler_product_items.adapter = ProductListsAdapter(productList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        val itemCart = menu.findItem(R.id.action_cart)
        mIcon = itemCart.icon as LayerDrawable

        badge.setBadgeCount(this, mIcon, mPresenter.getCountCart())
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cart) {
            val mIntent = Intent(this, CartnCheckoutActivity::class.java)
            this.startActivity(mIntent)
        }
        if (item.itemId == R.id.action_history) {
            val mIntent = Intent(this, OrderHistoryActivity::class.java)
            this.startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        mPresenter.destroy()
        super.onDestroy()
    }

}
