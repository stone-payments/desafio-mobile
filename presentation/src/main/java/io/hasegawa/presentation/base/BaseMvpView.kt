package io.hasegawa.presentation.base

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseMvpView<in ViewStateT> : MvpView {
    fun render(state: ViewStateT)
}