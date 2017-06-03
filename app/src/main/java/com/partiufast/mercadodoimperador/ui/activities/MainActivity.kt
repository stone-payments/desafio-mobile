package com.partiufast.mercadodoimperador.ui.activities

import android.app.Activity
import android.app.ActivityOptions.makeSceneTransitionAnimation
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.partiufast.mercadodoimperador.*
import com.partiufast.mercadodoimperador.callbacks.ProductFragmentCallback
import com.partiufast.mercadodoimperador.ui.fragments.CartFragment
import com.partiufast.mercadodoimperador.ui.fragments.ShopFragment
import kotlinx.android.synthetic.main.app_bar_nav_drawer.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ProductFragmentCallback, OnClickUpdateListCallback {

    private var sectionsPagerAdapter: SectionsPagerAdapter? = null
    private var viewPager: ViewPager? = null
    private var store: Store = Store()
    private var cartFragment: CartFragment? = null
    private var shopFragment: ShopFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        Fresco.initialize(applicationContext)


        sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.container) as ViewPager
        viewPager!!.adapter = sectionsPagerAdapter

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_local_grocery_store_grey_24dp)


        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

       updateDataOnNetworkAvailability()

    }

    private fun  isNetworkAvailable(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    private fun getJSONProducts() {
        doAsync {
            val request = ProductGetRequest("https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json")
            val list = request.run()

            uiThread {
                store.setAvailableProducts(list)
                shopFragment?.refreshAdapter()
                shopFragment?.setVisibilityProgressBar(View.GONE)
                Toast.makeText(applicationContext, "A força está presente em você!", Toast.LENGTH_SHORT ).show()
            }
        }
    }


    private fun updateDataOnNetworkAvailability(){
        if (isNetworkAvailable()) {
            getJSONProducts()
            Toast.makeText(applicationContext, "Rede Disponível", Toast.LENGTH_SHORT).show()

        }
        else {
            Toast.makeText(applicationContext, "Rede Indisponível", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onClickProductCard(position: Int, thumbnail_drawee_view: SimpleDraweeView) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra(getString(R.string.arg_product_intent), store.getAvailableProducts().get(position))
        if (Build.VERSION.SDK_INT >= 21) {
            val options = makeSceneTransitionAnimation(
                    this,
                    thumbnail_drawee_view,
                    resources.getString(R.string.transition_photo))
            startActivityForResult(intent, 1, options.toBundle())
        } else {
            startActivityForResult(intent, 1)
        }
    }

    override fun onClickUpdateButton() {
        updateDataOnNetworkAvailability()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Log.d("LOG D__", data!!.getStringExtra(getString(R.string.ADD_CART_BUTTON_EXTRA)))
                Toast.makeText(this, data!!.getParcelableExtra<Product>(getString(R.string.ADD_CART_BUTTON_EXTRA)).title, Toast.LENGTH_SHORT).show()
                store.addProductToCart(data.getParcelableExtra<Product>(getString(R.string.ADD_CART_BUTTON_EXTRA)))
                cartFragment?.refreshList()
                cartFragment?.updateListVisibility()
                tabs.getTabAt(1)?.select()
            }
            if (resultCode == Activity.RESULT_CANCELED){
                Log.d("aaa", "aaaa")
            }
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        if (id == R.id.nav_history) {
            // Handle the billing history activity
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }




    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            if (position == 0)
                return ShopFragment.newInstance(store.getAvailableProducts())
            if (position == 1)
                return CartFragment.newInstance(store.getCartProducts())
            return null
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val createdFragment = super.instantiateItem(container, position) as Fragment
            if (position == 0) {
                shopFragment = createdFragment as ShopFragment
            }
            if (position == 1) {
                cartFragment = createdFragment as CartFragment
            }
            return createdFragment
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Loja"
            //  1 -> return "Seu Carrinho"
            }
            return null
        }
    }




}

