package com.stone.desafiomobile.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stone.desafiomobile.R
import com.stone.desafiomobile.di.DaggerInjectionComponent
import com.stone.desafiomobile.di.DatabaseModule
import com.stone.desafiomobile.di.RetrofitModule
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.viewmodel.ProductsListVm

class ProductsListFragment : Fragment() {

    lateinit internal var mViewModel: ProductsListVm
    lateinit internal var mRecyclerView: RecyclerView
    lateinit internal var mAdapter: ProductsListAdapter

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
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_products_list, container, false)

        mRecyclerView = view.findViewById(R.id.products_list)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter = ProductsListAdapter()
        mRecyclerView.adapter = mAdapter

        return view
    }
}
