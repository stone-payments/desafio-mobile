package com.stone.desafiomobile.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.stone.desafiomobile.R
import com.stone.desafiomobile.di.DaggerInjectionComponent
import com.stone.desafiomobile.di.DatabaseModule
import com.stone.desafiomobile.di.InjectionComponent
import com.stone.desafiomobile.di.RetrofitModule
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.viewmodel.ProductsListVm

class MainActivity : AppCompatActivity() {

    lateinit internal var mViewModel: ProductsListVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProviders.of(this).get(ProductsListVm::class.java)


        val injectionComponent = DaggerInjectionComponent.builder()
                .retrofitModule(RetrofitModule())
                .databaseModule(DatabaseModule(this))
                .build()
        injectionComponent.inject(mViewModel)

        mViewModel.loadProducts()

        mViewModel.products.observe(this, Observer<List<Product>> { products ->
            Log.d(this::class.simpleName, "Produtos recuperados " + products.toString())
        })
    }
}
