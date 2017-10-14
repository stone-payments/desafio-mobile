package com.stone.desafiomobile.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.stone.desafiomobile.R
import com.stone.desafiomobile.di.DaggerInjectionComponent
import com.stone.desafiomobile.di.DatabaseModule
import com.stone.desafiomobile.di.RetrofitModule
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.viewmodel.ProductsListVm


class ProductsListFragment : Fragment(), ProductsListAdapter.ItemClickCallback {

    lateinit internal var mViewModel: ProductsListVm
    lateinit internal var mRecyclerView: RecyclerView
    lateinit internal var mAdapter: ProductsListAdapter

    lateinit internal var mBuyButton: Button

    lateinit internal var mEmptyListTV: TextView
    lateinit internal var mSwipeRefreshLayout: SwipeRefreshLayout

    internal var mCartItens: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(ProductsListVm::class.java)


        val injectionComponent = DaggerInjectionComponent.builder()
                .retrofitModule(RetrofitModule())
                .databaseModule(DatabaseModule(activity))
                .build()
        injectionComponent.inject(mViewModel)

        mViewModel.loadProducts()

        mViewModel.products.observe(this, Observer<List<Product>> { products ->
            Log.d(this::class.simpleName, "Produtos recuperados " + products.toString())
            if (products != null) {
                mAdapter.mValues = products
                if (!products.isEmpty()) {
                    mEmptyListTV.visibility = View.GONE
                }
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_products_list, container, false)

        mRecyclerView = view.findViewById(R.id.products_list)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter = ProductsListAdapter(this)
        mRecyclerView.adapter = mAdapter

        mBuyButton = view.findViewById(R.id.buy_button)
        mBuyButton.setOnClickListener { buyProduct() }

        mEmptyListTV = view.findViewById(R.id.empty_list_text)

        mSwipeRefreshLayout = view.findViewById(R.id.swipeContainer)
        mSwipeRefreshLayout.setOnRefreshListener { onRefresh() }

        return view
    }

    fun onRefresh() {
        mViewModel.loadProducts({ mSwipeRefreshLayout.isRefreshing = false })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(BUNDLE_CART, mCartItens)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        mCartItens = savedInstanceState?.getSerializable(BUNDLE_CART) as ArrayList<Product>? ?: mCartItens
        super.onViewStateRestored(savedInstanceState)
    }

    companion object {
        val BUNDLE_CART = "bundle_cart"
    }

    override fun addToCart(product: Product) {
        mCartItens.add(product)
        mBuyButton.visibility = View.VISIBLE
    }

    override fun removeFromCart(product: Product) {
        mCartItens.remove(product)
        if (mCartItens.isEmpty()) {
            mBuyButton.visibility = View.GONE
        }
    }

    fun buyProduct() {
        val intent = Intent(activity, CheckoutActivity::class.java)
        intent.putExtra(CheckoutActivity.ARG_CART, mCartItens)
        startActivity(intent)
    }
}
