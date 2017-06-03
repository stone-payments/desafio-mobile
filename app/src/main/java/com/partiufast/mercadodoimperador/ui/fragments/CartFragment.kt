package com.partiufast.mercadodoimperador.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.partiufast.mercadodoimperador.Product
import com.partiufast.mercadodoimperador.R
import com.partiufast.mercadodoimperador.adapters.CartListAdapter
import com.partiufast.mercadodoimperador.ui.activities.PaymentActivity
import java.math.BigDecimal


class CartFragment : Fragment() {
    companion object {
        private val ARG_CART_LIST = "ARG_CART_LIST"
        private var cartProducts: java.util.ArrayList<Product>? = null
        private var recyclerView: RecyclerView? = null
        private var recyclerViewLinearLayout: LinearLayout? = null
        private var emptyCartLinearLayout: LinearLayout? = null

        fun newInstance(cartProducts: ArrayList<Product>): CartFragment {
            val fragment = CartFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_CART_LIST, cartProducts)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            cartProducts = arguments.getParcelableArrayList<Product>(ARG_CART_LIST)
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.cart_fragment, container, false)
        recyclerView = rootView.findViewById(R.id.cart_list_recycler_view) as RecyclerView
        recyclerViewLinearLayout = rootView.findViewById(R.id.recycler_view_layout) as LinearLayout
        emptyCartLinearLayout = rootView.findViewById(R.id.button_layout) as LinearLayout
        val emptyCartButton = rootView.findViewById(R.id.empty_cart_button) as Button
        val checkoutButton = rootView.findViewById(R.id.checkoutButton) as Button

        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerView!!.adapter = CartListAdapter(cartProducts!!)

        val tabLayout = activity.findViewById(R.id.tabs) as TabLayout
        emptyCartButton.setOnClickListener {
            tabLayout.getTabAt(0)?.select()
        }

        checkoutButton.setOnClickListener{
            var total_value = BigDecimal(0)
            for(index in 0..cartProducts!!.size-1) {
                Log.d("item_value", cartProducts!!.get(index).price.toPlainString())
                total_value = total_value.add(cartProducts!!.get(index).price.multiply(BigDecimal(cartProducts!!.get(index).productCount)))
            }

            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra(getString(R.string.CART_VALUE_EXTRA), total_value.toString())
                    .putExtra(getString(R.string.CART_VALUE_PLAIN_EXTRA), total_value.toPlainString())
            startActivity(intent)
        }

        return rootView
    }

    fun refreshList() {
        recyclerView!!.adapter.notifyDataSetChanged()
    }

    fun updateListVisibility() {
        if (cartProducts?.size!! > 0) {
            if (recyclerViewLinearLayout?.visibility == View.INVISIBLE) {
                recyclerViewLinearLayout?.visibility = View.VISIBLE
                emptyCartLinearLayout?.visibility = View.INVISIBLE
            }
        } else {
            if (recyclerViewLinearLayout?.visibility == View.VISIBLE) {
                recyclerViewLinearLayout?.visibility = View.INVISIBLE
                emptyCartLinearLayout?.visibility = View.VISIBLE
            }
        }
    }


}
