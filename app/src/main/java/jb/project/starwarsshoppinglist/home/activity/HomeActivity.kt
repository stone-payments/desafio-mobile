package jb.project.starwarsshoppinglist.home.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import jb.project.starwarsshoppinglist.BaseActivity
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.home.activity.view.HomeView
import jb.project.starwarsshoppinglist.productList.adapter.SectionsPagerAdapter
import jb.project.starwarsshoppinglist.home.presenter.HomePresenter
import jb.project.starwarsshoppinglist.home.presenter.HomePresenterImpl
import jb.project.starwarsshoppinglist.model.Product
import jb.project.starwarsshoppinglist.home.adapter.ProductListsAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(), HomeView {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var mPresenter: HomePresenter
    var mTabText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mPresenter = HomePresenterImpl()
        mPresenter.init(this)

    }

    override fun loadProductList(productList: ArrayList<Product>) {
        recyler_product_items.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyler_product_items.adapter = ProductListsAdapter(productList)
    }


}
