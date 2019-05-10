package br.com.stone.vianna.starstore.view.itemList

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.stone.vianna.starstore.entity.Item
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.baseClasses.BaseActivity
import br.com.stone.vianna.starstore.extensions.hide
import br.com.stone.vianna.starstore.extensions.show
import kotlinx.android.synthetic.main.activity_item_list.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ItemListActivity : BaseActivity(), ItemListContract.View {

    private val presenter: ItemListContract.Presenter by inject { parametersOf(this) }

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
        val adapter = ItemsAdapter(items) { _: Item, _: View ->
        }
        base_item_list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
        presenter.init()
    }


}