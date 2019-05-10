package br.com.stone.vianna.starstore.view.itemList

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.baseClasses.BaseActivity
import br.com.stone.vianna.starstore.extensions.hide
import br.com.stone.vianna.starstore.extensions.show
import br.com.stone.vianna.starstore.view.shoppingCart.ShoppingCartActivity
import kotlinx.android.synthetic.main.activity_item_list.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ItemListActivity : BaseActivity(), ItemListContract.View {

    private val presenter: ItemListContract.Presenter by inject { parametersOf(this) }
    private var textCartItemCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        initializeViews()
    }

    override fun displayLoading() {
        service_loading?.show()
    }

    override fun hideLoading() {
        service_loading?.hide()
    }

    private fun initializeViews() {
        toolbar.title = getString(R.string.items_list_screen_title)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))

        val layoutManager = LinearLayoutManager(this)
        base_item_list.layoutManager = layoutManager
    }

    override fun updateListItems(items: List<Item>) {
        val adapter = ItemsAdapter(items) { item: Item, _: View ->
            presenter.onItemClicked(item)
        }
        base_item_list.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.cart_menu, menu)
        val menuItem = menu.findItem(R.id.actionNotifications)
        val actionView = menuItem.actionView
        textCartItemCount = actionView.findViewById(R.id.cart_badge) as TextView
        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }
        presenter.init()
        return true
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    override fun setupBadge(number: Int) {
        textCartItemCount?.let {
            when (number) {
                0 -> it.hide()
                else -> {
                    it.show()
                    it.text = "${Math.min(number, 99)}"
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionNotifications -> {
                presenter.onCartIconClicked()
                true
            }
            else -> {
                return true
            }
        }
    }

    override fun openShoppingCart() {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
    }


}