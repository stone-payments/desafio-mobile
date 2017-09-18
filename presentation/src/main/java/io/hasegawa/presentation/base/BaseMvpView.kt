package io.hasegawa.presentation.base

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseMvpView<in ViewStateT> : MvpView {
    /**
     * The main MVI render function. It should be used by the view to apply the [ViewStateT] to the
     * view elements.
     */
    fun render(state: ViewStateT)
}