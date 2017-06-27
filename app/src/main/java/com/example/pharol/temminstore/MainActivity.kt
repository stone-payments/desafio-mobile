package com.example.pharol.temminstore

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.pharol.temminstore.shoppingcart.ShoppingCartActivity
import com.example.pharol.temminstore.transaction.TransactionFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
MainActivityFragment.onItemAddedToCartListener {
    private var hasItems: Boolean = false

    override fun onItemAdded(size: Int) {
        hasItems = size >0

        choose_cart_icon(menu)
    }

    private fun choose_cart_icon(menu: Menu?) {
        if (hasItems)
            menu?.getItem(0)?.icon = resources.getDrawable(R.drawable.ic_shopping_cart_full)
        else
            menu?.getItem(0)?.icon = resources.getDrawable(R.drawable.ic_shopping_cart_white_48dp)
    }

    private val MY_PERMISSIONS_REQUEST_INTERNET: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar= findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)


        supportFragmentManager.beginTransaction().replace(R.id.fragment_main,MainActivityFragment(),"Temmin Store").commit()

        val permission = "android.permission.INTERNET"
        if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED)

        ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.INTERNET),
                    MY_PERMISSIONS_REQUEST_INTERNET)


    }

    override fun onStart() {
        super.onStart()
    }

    private var menu: Menu? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        choose_cart_icon(menu)
        this.menu = menu
        return true
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.ic_shopping_cart){
            if (hasItems)
                startActivity(Intent(this, ShoppingCartActivity::class.java))
            else
                Toast.makeText(this,"Empty Cart!! Please add items.",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment : android.support.v4.app.Fragment
        when (item.itemId) {
            R.id.nav_main -> {
                fragment = MainActivityFragment()
                supportActionBar?.title = "Temmin Store"
            }
            R.id.nav_transactions -> {
                fragment = TransactionFragment()
                supportActionBar?.title = "Transactions History"
            }
            else ->
                fragment = MainActivityFragment()
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_main, fragment)
                .commit()
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}
