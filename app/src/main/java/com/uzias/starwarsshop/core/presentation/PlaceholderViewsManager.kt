package com.uzias.starwarsshop.core.presentation

import android.view.View

class PlaceholderViewsManager(private val loadingViewStub: View,
                              private val emptyViewStub: View,
                              private val errorViewStub: View,
                              private val containerView: View) {

    fun showLoading() {
        hideAll()
        setViewVisibility(loadingViewStub, View.VISIBLE)
    }

    fun showEmpty() {
        hideAll()
        setViewVisibility(emptyViewStub, View.VISIBLE)
    }

    fun showError() {
        hideAll()
        setViewVisibility(errorViewStub, View.VISIBLE)
    }

    fun hideAll() {
        hideLoading()
        hideEmpty()
        hideError()
    }

    fun hideLoading() {
        setViewVisibility(loadingViewStub, View.GONE)
    }

    fun hideEmpty() {
        setViewVisibility(emptyViewStub, View.GONE)
    }

    fun hideError() {
        setViewVisibility(errorViewStub, View.GONE)
    }

    private fun setViewVisibility(view: View, visibility: Int) {
        view.visibility = visibility
        containerView.visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

}
