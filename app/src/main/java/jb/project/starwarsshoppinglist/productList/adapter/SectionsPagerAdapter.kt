package jb.project.starwarsshoppinglist.productList.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import jb.project.starwarsshoppinglist.model.Product
import jb.project.starwarsshoppinglist.productList.fragment.ProductListFragment

/**
 * Created by Jb on 12/10/2017.
 */
class SectionsPagerAdapter(fm: FragmentManager, private val amountTabs: Int, private val list: ArrayList<Product>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ProductListFragment.newInstance(list)
    }

    override fun getCount(): Int {
        // Show total pages plus one because I manually added "TODOS" page
        return amountTabs + 1
    }
}