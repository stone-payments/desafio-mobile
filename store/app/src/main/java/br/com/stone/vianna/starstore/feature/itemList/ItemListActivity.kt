package br.com.stone.vianna.starstore.feature.itemList

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.baseClasses.BaseActivity
import br.com.stone.vianna.starstore.extensions.hide
import br.com.stone.vianna.starstore.extensions.show
import br.com.stone.vianna.starstore.feature.shoppingCart.ShoppingCartActivity
import br.com.stone.vianna.starstore.feature.transactionHistory.TransactionHistoryActivity
import kotlinx.android.synthetic.main.activity_item_list.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ItemListActivity : BaseActivity(), ItemListContract.View {


    private val presenter: ItemListContract.Presenter by inject { parametersOf(this) }
    private var textCartItemCount: TextView? = null
    private val adapter = ItemsAdapter { item: Item ->
        presenter.onItemClicked(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        initializeViews()
        presenter.init()
    }

    private fun initializeViews() {
        toolbar.title = getString(R.string.items_list_screen_title)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))
        toolbar.navigationIcon = resources.getDrawable(R.mipmap.ic_history, theme)
        toolbar.setNavigationOnClickListener { presenter.onHistoryIconClicked() }

        base_item_list.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        base_item_list.layoutManager = layoutManager
    }

    override fun updateListItems(items: List<Item>) {
        error_message.hide()
        adapter.updateItems(items)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.cart_menu, menu)
        val menuItem = menu.findItem(R.id.actionNotifications)
        val actionView = menuItem.actionView

        textCartItemCount = actionView.findViewById(R.id.cart_badge) as TextView
        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }
        presenter.updateBadge()
        return true
    }

    override fun showError(error: String) {
        error_message.text = error
        error_message.show()
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

    override fun displayEmptyView() {
        error_message.text = getString(R.string.empty_list_text)
        error_message.show()
    }

    override fun openShoppingCart() {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
    }

    override fun openHistory() {
        val intent = Intent(this, TransactionHistoryActivity::class.java)
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        presenter.clearEvents()
    }

    override fun displayLoading() {
        service_loading?.show()
    }

    override fun hideLoading() {
        service_loading?.hide()
    }

}