package com.example.pharol.temminstore.di.ui

import android.app.Activity
import android.content.Context
import com.example.pharol.temminstore.di.PerActivity
import dagger.Module
import dagger.Provides


@PerActivity
@Module
class ActivityModule(val mActivity: Activity){

    @Provides
    fun provideContext(): Context = mActivity.baseContext

}
