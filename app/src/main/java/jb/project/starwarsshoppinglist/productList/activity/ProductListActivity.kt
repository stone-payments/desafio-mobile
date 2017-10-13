package jb.project.starwarsshoppinglist.productList.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Menu
import android.view.MenuItem
import jb.project.starwarsshoppinglist.BaseActivity
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.home.adapter.SectionsPagerAdapter
import jb.project.starwarsshoppinglist.model.Product
import jb.project.starwarsshoppinglist.productList.activity.view.ProductListView
import jb.project.starwarsshoppinglist.productList.presenter.ProductListPresenter
import jb.project.starwarsshoppinglist.productList.presenter.ProductListPresenterImpl
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : BaseActivity(), ProductListView {
    override fun showError(strResId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(strResId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var mPresenter: ProductListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        mPresenter = ProductListPresenterImpl()
        mPresenter.init(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_product_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_search) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun loadProductList(productList: MutableList<Product>) {
        // Set up the ViewPager with the sections adapter.
        val tittleList = getAllTabsNames(productList)

        tabs.addTab(tabs.newTab().setText(R.string.all_products))
        for (tittle : String in tittleList)
        {
            tabs.addTab(tabs.newTab().setText(tittle))
        }

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, tittleList)

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        container.adapter = mSectionsPagerAdapter
    }

    private fun getAllTabsNames(productList: MutableList<Product>): List<String> {
        val tabsList: MutableList<String> = mutableListOf()

        productList.mapTo(tabsList) { it.type!! }
        return tabsList.distinct()

    }


}
