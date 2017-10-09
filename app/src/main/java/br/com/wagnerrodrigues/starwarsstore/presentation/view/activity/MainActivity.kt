package br.com.wagnerrodrigues.starwarsstore.presentation.view.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment.CartFragment
import br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment.MainFragment
import br.com.wagnerrodrigues.starwarsstore.presentation.view.fragment.PurchasesFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import starwarsstore.wagnerrodrigues.com.br.starwarsstore.R


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val mainFragment = Fragment.instantiate(this@MainActivity, MainFragment::class.java.name) as MainFragment
        supportFragmentManager.beginTransaction().replace(R.id.main_layout, mainFragment).commit()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        shouldDisplayHomeUp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            shouldDisplayHomeUp()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_cart -> {
                showCart()
            }
            R.id.nav_purchases -> {
                showMyPurchases()
            }
        }
        return true
    }

    fun shouldDisplayHomeUp() {
        val canback = supportFragmentManager.backStackEntryCount > 0
        if(!canback) {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            val drawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_open)
            drawer_layout.addDrawerListener(drawerToggle)
            drawerToggle.syncState()
        }else{
            supportActionBar!!.setHomeButtonEnabled(false)
            toolbar.setNavigationOnClickListener{
                onBackPressed()
            }
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }


    fun showCart() {
        val cartFragment = Fragment.instantiate(this, CartFragment::class.java.name) as CartFragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction
                .replace(R.id.main_layout, cartFragment)
                .addToBackStack(cartFragment::class.java.name)
                .commit()
    }

    private fun showMyPurchases() {
        val purchasesFragment = Fragment.instantiate(this, PurchasesFragment::class.java.name) as PurchasesFragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction
                .replace(R.id.main_layout, purchasesFragment)
                .addToBackStack(purchasesFragment::class.java.name)
                .commit()
    }

}
