    package com.example.pharol.temminstore.di.ui

import android.content.Context
import com.example.pharol.temminstore.item.ItemDetailsActivity
import com.example.pharol.temminstore.di.PerActivity
import com.example.pharol.temminstore.item.ItemRecyclerAdapter
import com.example.pharol.temminstore.payment.PaymentInfoActivity
import dagger.Component
import com.example.pharol.temminstore.di.ApplicationComponent

    @PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mActivity: PaymentInfoActivity)

    fun inject(mActivity: ItemDetailsActivity)

    fun inject(itemRecyclerAdapter: ItemRecyclerAdapter)

    fun getApplication() : Context

}