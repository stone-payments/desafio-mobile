package jb.project.starwarsshoppinglist.productList.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Menu
import android.view.MenuItem
import jb.project.starwarsshoppinglist.BaseActivity
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.productList.adapter.SectionsPagerAdapter
import jb.project.starwarsshoppinglist.model.Product
import jb.project.starwarsshoppinglist.productList.activity.view.ProductListView
import jb.project.starwarsshoppinglist.productList.presenter.ProductListPresenter
import jb.project.starwarsshoppinglist.productList.presenter.ProductListPresenterImpl
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : BaseActivity(), ProductListView {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var mPresenter: ProductListPresenter
    var mTabText: String = ""

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

    override fun loadProductList(productList: ArrayList<Product>) {
        // Set up the ViewPager with the sections adapter.
        val tabsList = getAllTabsNames(productList)

        tabs.addTab(tabs.newTab().setText(R.string.all_products))
        for (tittle: String in tabsList) {
            tabs.addTab(tabs.newTab().setText(tittle))
        }
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, tabsList.size, productList)

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mTabText = tab.text.toString()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        container.adapter = mSectionsPagerAdapter
    }


     fun getCurrentTabText() : String {
        return mTabText
    }

    private fun getAllTabsNames(productList: ArrayList<Product>): List<String> {
        val tabsList: ArrayList<String> = ArrayList()
        productList.mapTo(tabsList) { it.type!! }
        return tabsList.distinct()
    }


}
