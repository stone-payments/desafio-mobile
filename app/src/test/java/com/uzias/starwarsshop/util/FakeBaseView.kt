package com.uzias.starwarsshop.util

import com.nhaarman.mockito_kotlin.mock
import com.uzias.starwarsshop.core.presentation.BasePresenter
import com.uzias.starwarsshop.core.presentation.BaseView
import io.reactivex.functions.Action

class FakeBaseView: BaseView {

    private val showEmptyAction: Action = mock()
    private val hideEmptyAction: Action = mock()
    private val hideErrorAction: Action = mock()
    private val showLoadingAction: Action = mock()
    private val hideLoadingAction: Action = mock()
    private val reportNetworkingErrorAction: Action = mock()
    private val enableRefresh: Action = mock()
    private val disableRefresh: Action = mock()
    private val showErrorAction: Action = mock()

    override fun showEmptyState(): Action = showEmptyAction

    override fun hideEmptyState(): Action = hideEmptyAction

    override fun showErrorState(): Action = showErrorAction

    override fun hideErrorState(): Action = hideErrorAction

    override fun showLoading(): Action = showLoadingAction

    override fun hideLoading(): Action = hideLoadingAction

    override fun disableRefresh(): Action = disableRefresh

    override fun enableRefresh(): Action = enableRefresh

    override fun networkingErrorState(): Action = reportNetworkingErrorAction

    override fun getPresenter(): BasePresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun injectDependencies() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}