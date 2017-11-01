package com.uzias.starwarsshop.menu.presentation.view

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.uzias.starwarsshop.R
import com.uzias.starwarsshop.checkout.presentation.view.CheckoutActivity
import com.uzias.starwarsshop.core.presentation.BaseActivity
import com.uzias.starwarsshop.core.presentation.BasePresenter
import com.uzias.starwarsshop.menu.presentation.presenter.MenuPresenter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

interface MenuView {
    fun closeDrawerMenu()
    fun setTitleProducts()
    fun setTitleTransactions()
}

class MenuActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MenuView {

    @Inject
    lateinit var presenter: MenuPresenter

    private val drawerToggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawerlayout,
                R.string.app_name, R.string.app_name)
    }

    override fun getPresenter(): BasePresenter = presenter

    override fun injectDependencies() {
        AndroidInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerlayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        injectDependencies()
        placeHolder.hideAll()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }


    override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        menuSelected(item.itemId)
        return true
    }

    private fun menuSelected(itemSelected: Int){
        when(itemSelected) {
            R.id.item_products -> presenter.clickedMenuProducts()
            R.id.item_transactions -> presenter.clickedMenuTransactions()
        }
    }

    override fun closeDrawerMenu() {
        drawerlayout.closeDrawers()
    }

    override fun setTitleProducts() {
        setTitle(getString(R.string.item_products))
    }

    override fun setTitleTransactions() {
        setTitle(getString(R.string.item_transactions))
    }

    private fun setTitle(title: String){
        supportActionBar?.let {
            it.title = title
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CheckoutActivity.CHECKOUT_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            presenter.resultTransactionOk()
        }
    }

}