package com.uzias.starwarsshop.core.behaviours.loadingstate

import com.uzias.starwarsshop.core.behaviours.ShowAtStartHideWhenDone
import com.uzias.starwarsshop.core.presentation.LoadingView
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import org.reactivestreams.Publisher

class LoadingCoordination<T>(private val view: LoadingView,
                             private val uiScheduler: Scheduler) : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {

        val delegate = ShowAtStartHideWhenDone<T>(
                view.showLoading(),
                view.hideLoading(),
                uiScheduler
        )

        return upstream.compose(delegate)
    }

}
