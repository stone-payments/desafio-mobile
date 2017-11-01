package com.uzias.starwarsshop.util

import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.uzias.starwarsshop.core.presentation.*
import com.uzias.starwarsshop.core.util.MockitoHelpers.oneTimeOnly
import org.mockito.Mockito.inOrder


class BehavioursRobot
private constructor(private val target: Any) {

    @Throws(Exception::class)
    fun shouldShowErrorState(): BehavioursRobot {
        checkErrorStateView()
        val view = target as ErrorStateView
        verify(view.showErrorState(), oneTimeOnly()).run()
        return this
    }

    @Throws(Exception::class)
    fun shouldNotShowErrorState(): BehavioursRobot {
        checkErrorStateView()
        val view = target as ErrorStateView
        verify(view.showErrorState(), never()).run()
        return this
    }

    @Throws(Exception::class)
    fun shouldShowEmptyState(): BehavioursRobot {
        checkEmptyStateView()
        val view = target as EmptyStateView
        verify(view.showEmptyState(), oneTimeOnly()).run()
        return this
    }

    @Throws(Exception::class)
    fun shouldNotShowEmptyState(): BehavioursRobot {
        checkEmptyStateView()
        val view = target as EmptyStateView
        verify(view.showEmptyState(), never()).run()
        return this
    }

    @Throws(Exception::class)
    fun shouldReportNetworkingError(): BehavioursRobot {
        checkNetworkingErrorView()
        val view = target as NetworkingView
        verify(view.networkingErrorState(), oneTimeOnly()).run()
        return this
    }

    @Throws(Exception::class)
    fun shouldNotReportNetworkingError(): BehavioursRobot {
        checkNetworkingErrorView()
        val view = target as NetworkingView
        verify(view.networkingErrorState(), never()).run()
        return this
    }

    @Throws(Exception::class)
    fun showLoadingFirstHideLoadingAfter(): BehavioursRobot {
        checkLoadingView()
        val view = target as LoadingView
        verifyInOrderShowAndHideLoading(view)
        return this
    }

    @Throws(Exception::class)
    fun disableRefreshFirstAndEnableAfter(): BehavioursRobot {
        checkToogleRefreshView()
        val view = target as ToogleRefreshView
        verifyDisableEnableRefresh(view)
        return this
    }

    @Throws(Exception::class)
    fun disableRefreshFirstAndNotEnableAfter(): BehavioursRobot {
        checkToogleRefreshView()
        val view = target as ToogleRefreshView
        verifyDisableAndNotEnableRefresh(view)
        return this
    }

    @Throws(Exception::class)
    private fun verifyInOrderShowAndHideLoading(view: LoadingView) {
        val inOrder = inOrder(view.showLoading(), view.hideLoading())
        inOrder.verify(view.showLoading(), oneTimeOnly()).run()
        inOrder.verify(view.hideLoading(), oneTimeOnly()).run()
    }

    @Throws(Exception::class)
    private fun verifyDisableEnableRefresh(view: ToogleRefreshView) {
        val inOrder = inOrder(view.enableRefresh(), view.disableRefresh())
        inOrder.verify(view.disableRefresh(), oneTimeOnly()).run()
        inOrder.verify(view.enableRefresh(), oneTimeOnly()).run()
    }

    @Throws(Exception::class)
    private fun verifyDisableAndNotEnableRefresh(view: ToogleRefreshView) {
        val inOrder = inOrder(view.enableRefresh(), view.disableRefresh())
        inOrder.verify(view.disableRefresh(), oneTimeOnly()).run()
        inOrder.verify(view.enableRefresh(), never()).run()
    }

    private fun checkEmptyStateView() {
        if (target !is EmptyStateView)
            throw IllegalStateException("Target view not instance of EmptyStateView")
    }

    private fun checkErrorStateView() {
        if (target !is ErrorStateView)
            throw IllegalStateException("Target view not instance of ErrorStateView")
    }

    private fun checkLoadingView() {
        if (target !is LoadingView)
            throw IllegalStateException("Target view not instance of LoadingView")
    }

    private fun checkNetworkingErrorView() {
        if (target !is NetworkingView)
            throw IllegalStateException("Target view not instance of NetworkingErroView")
    }

    private fun checkToogleRefreshView() {
        if (target !is ToogleRefreshView)
            throw IllegalStateException("Target view not instance of ToogleRefreshView")
    }

    companion object {
        fun with(target: Any): BehavioursRobot {
            return BehavioursRobot(target)
        }
    }


}
