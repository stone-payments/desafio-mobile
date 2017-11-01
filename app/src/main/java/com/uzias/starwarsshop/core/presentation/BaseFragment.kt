package com.uzias.starwarsshop.core.presentation

import android.arch.lifecycle.LifecycleRegistry
import android.support.v4.app.Fragment
import io.reactivex.functions.Action
import javax.inject.Inject

abstract class BaseFragment : Fragment(), BaseView {

    @Inject
    lateinit var placeHolder: PlaceholderViewsManager

    override fun getLifecycle(): LifecycleRegistry {
        return LifecycleRegistry(this)
    }

    override fun showLoading(): Action = Action { placeHolder.showLoading() }

    override fun hideLoading(): Action = Action { placeHolder.hideLoading() }

    override fun hideEmptyState(): Action = Action { placeHolder.hideEmpty() }

    override fun showEmptyState(): Action = Action { placeHolder.showEmpty() }

    override fun showErrorState(): Action = Action { placeHolder.showError() }

    override fun hideErrorState(): Action = Action { placeHolder.hideError() }

    override fun enableRefresh(): Action = Action {}
    override fun disableRefresh(): Action = Action {}

    override fun networkingErrorState(): Action = Action {
        placeHolder.showError() }

}