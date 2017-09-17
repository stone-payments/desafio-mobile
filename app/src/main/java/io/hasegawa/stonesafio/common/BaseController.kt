package io.hasegawa.stonesafio.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.RestoreViewOnCreateMviController
import io.hasegawa.presentation.base.BaseMvpView
import io.hasegawa.presentation.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference


abstract class BaseController<ViewT : BaseMvpView<ViewStateT>, ViewStateT, EventsT,
        PresenterT : BasePresenter<ViewT, ViewStateT, EventsT>>(bundle: Bundle)
    : RestoreViewOnCreateMviController<ViewT, PresenterT>(bundle), KotterKnife {

    constructor() : this(Bundle.EMPTY)

    override var kotterView: WeakReference<View>? = null

    private var restoringViewState = false
    private val fullDisposables = CompositeDisposable()
    private val viewDisposables = CompositeDisposable()
    private val attachDisposables = CompositeDisposable()

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup,
                             savedViewState: Bundle?): View

    open fun onViewBound() = Unit

    open fun onRestoringViewStateChange(newState: Boolean) = Unit

    override final fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                                    savedViewState: Bundle?): View {
        val view = inflateView(inflater, container, savedViewState)
        KotterKnife.bind(this, view)
        onViewBound()
        return view
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        attachDisposables.clear()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        KotterKnife.unbind(this)
        viewDisposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        fullDisposables.clear()
    }

    override final fun setRestoringViewState(restoringViewState: Boolean) {
        onRestoringViewStateChange(restoringViewState)
        this.restoringViewState = restoringViewState
    }

}