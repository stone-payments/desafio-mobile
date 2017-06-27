package com.example.pharol.temminstore.di.ui

import android.arch.lifecycle.LifecycleFragment
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class FragmentModule(val mActivity: LifecycleFragment){

    @Provides
    fun provideContext(): Context = mActivity.context

}
