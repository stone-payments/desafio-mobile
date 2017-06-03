package com.partiufast.mercadodoimperador.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.partiufast.mercadodoimperador.Product
import com.partiufast.mercadodoimperador.R
import com.partiufast.mercadodoimperador.adapters.AvailableListAdapter
import com.partiufast.mercadodoimperador.ui.activities.OnClickUpdateListCallback
import kotlinx.android.synthetic.main.shop_fragment.*

class ShopFragment : Fragment() {
    companion object {
        private val ARG_SHOP_LIST = "ARG_SHOP_LIST"
        private var availableProducts: java.util.ArrayList<Product>? = null
        //private var  recyclerView: RecyclerView? = null


        fun newInstance(availableProducts: ArrayList<Product>): ShopFragment {
            val fragment = ShopFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_SHOP_LIST, availableProducts)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            availableProducts = arguments.getParcelableArrayList<Product>(ARG_SHOP_LIST)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.shop_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        product_list_recycler_view.layoutManager = LinearLayoutManager(context)
        product_list_recycler_view.layoutManager = LinearLayoutManager(context)
        product_list_recycler_view.adapter = AvailableListAdapter(availableProducts!!)
        updateViewsOnNetworkAvailability()
        update_button.setOnClickListener {
            val updateButtonCallback = activity as OnClickUpdateListCallback
            updateButtonCallback.onClickUpdateButton()
            updateViewsOnNetworkAvailability()
        }
    }

    fun refreshAdapter() {
        product_list_recycler_view.adapter.notifyDataSetChanged()
    }

    fun setVisibilityProgressBar(visibility: Int) {
        progress_bar.visibility = visibility
    }

    fun setVisibilityUpdateButton(visibility: Int) {
        update_button.visibility = visibility
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }


    private fun updateViewsOnNetworkAvailability() {
        if (isNetworkAvailable()) {
            setVisibilityProgressBar(View.VISIBLE)
            setVisibilityUpdateButton(View.GONE)
        } else {
            setVisibilityProgressBar(View.GONE)
            setVisibilityUpdateButton(View.VISIBLE)
        }

    }


}