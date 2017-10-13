package jb.project.starwarsshoppinglist.home.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import jb.project.starwarsshoppinglist.productList.fragment.PlaceholderFragment

/**
 * Created by Jb on 12/10/2017.
 */
class SectionsPagerAdapter(fm: FragmentManager, val item: List<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        // Show total pages.
        return item.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return item[position]
    }

}