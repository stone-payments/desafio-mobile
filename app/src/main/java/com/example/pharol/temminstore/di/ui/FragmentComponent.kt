    package com.example.pharol.temminstore.di.ui

import android.content.Context
import com.example.pharol.temminstore.MainActivityFragment
import com.example.pharol.temminstore.di.ApplicationComponent
import com.example.pharol.temminstore.di.PerActivity
import com.example.pharol.temminstore.item.ItemRecyclerAdapter
import com.example.pharol.temminstore.transaction.TransactionFragment
import dagger.Component
import com.example.pharol.temminstore.shoppingcart.ShoppingCartActivityFragment

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(mFragment: MainActivityFragment)

    fun inject(mFragment: TransactionFragment)

    fun inject(mFragment: ShoppingCartActivityFragment)

    fun inject(itemRecyclerAdapter: ItemRecyclerAdapter)

    fun getFragmentContext(): Context

}