package jb.project.starwarsshoppinglist.productList.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import jb.project.starwarsshoppinglist.R
import jb.project.starwarsshoppinglist.model.Product
import jb.project.starwarsshoppinglist.productList.activity.ProductListActivity
import jb.project.starwarsshoppinglist.home.adapter.ProductListsAdapter
import kotlinx.android.synthetic.main.fragment_product_list.view.*


/**
 * Created by Jb on 13/10/2017.
 */

/**
 * A placeholder fragment containing a simple view.
 */
class ProductListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_product_list, container, false)
        val mListProducts: ArrayList<Product> = arguments.getParcelableArrayList(ARG_SECTION)
        val mTabName = (activity as ProductListActivity).getCurrentTabText()

        val obj_adapter = if (mTabName == "" || mTabName == getString(R.string.all_products)) {
            ProductListsAdapter(mListProducts)
        } else {
            val sortedList = mListProducts.sortedWith(compareBy({ it.type }))
            val namedList: List<Product> =
                    sortedList.subList(
                            sortedList.indexOfFirst { it.type.equals(mTabName) },
                            sortedList.indexOfLast { it.type.equals(mTabName) }
                    )
            ProductListsAdapter(namedList)
        }

        rootView.recyler_product_items.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        rootView.recyler_product_items.swapAdapter(obj_adapter, true)
        rootView.recyler_product_items.scrollBy(0, 0)


        return rootView
    }

    companion object {
        private val ARG_SECTION = "list_of_elements"
        fun newInstance(list: ArrayList<Product>): ProductListFragment {
            val fragment = ProductListFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_SECTION, list)
            fragment.arguments = args
            return fragment
        }
    }
}