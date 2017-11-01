package com.uzias.starwarsshop.core.behaviours.errorstate

import com.uzias.starwarsshop.core.behaviours.HideAtStartShowAtError
import com.uzias.starwarsshop.core.errors.ContentNotFoundError
import com.uzias.starwarsshop.core.presentation.ErrorStateView
import com.uzias.starwarsshop.util.ErrorPredicate
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import org.reactivestreams.Publisher

class AssignErrorCoordination<T>(private val view: ErrorStateView, private val uiScheduler: Scheduler):
        FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {

        val delegate = HideAtStartShowAtError<T>(
                whenStart = view.hideErrorState(),
                atError = view.showErrorState(),
                errorPredicate = object : ErrorPredicate {
                    override fun evaluate(error: Throwable): Boolean = error !is ContentNotFoundError
                },
                targetScheduler = uiScheduler
        )

        return upstream.compose(delegate)
    }

}
