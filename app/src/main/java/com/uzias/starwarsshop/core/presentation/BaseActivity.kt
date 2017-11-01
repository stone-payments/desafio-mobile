package com.uzias.starwarsshop.core.presentation

import android.arch.lifecycle.LifecycleRegistry
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.functions.Action
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector

abstract class BaseActivity : AppCompatActivity(), BaseView, HasSupportFragmentInjector {

    @Inject
    lateinit var placeHolder: PlaceholderViewsManager

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun getLifecycle(): LifecycleRegistry {
        return LifecycleRegistry(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading(): Action = Action {
        placeHolder.showLoading() }

    override fun hideLoading(): Action = Action { placeHolder.hideLoading() }

    override fun hideEmptyState(): Action = Action { placeHolder.hideEmpty() }

    override fun showEmptyState(): Action = Action { placeHolder.showEmpty() }

    override fun showErrorState(): Action = Action { placeHolder.showError() }

    override fun hideErrorState(): Action = Action { placeHolder.hideError() }

    override fun enableRefresh(): Action = Action {}

    override fun disableRefresh(): Action = Action {}

    override fun networkingErrorState(): Action = Action {
        placeHolder.showError() }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}