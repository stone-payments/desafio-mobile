package com.uzias.starwarsshop.core.behaviours

import com.uzias.starwarsshop.util.ErrorPredicate
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import io.reactivex.functions.Action
import org.reactivestreams.Publisher

class HideAtStartShowAtError<T>(private val whenStart: Action,
                                private val atError: Action,
                                private val errorPredicate: ErrorPredicate,
                                private val targetScheduler: Scheduler) : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream
                .doOnSubscribe{ hide() }
                .doOnError({evaluateAndShowIfApplicable(it)})
    }

    private fun evaluateAndShowIfApplicable(throwable: Throwable) {
        if (errorPredicate.evaluate(throwable)) {
            subscribeAndFireAction(atError)
        }
    }

    private fun hide() {
        subscribeAndFireAction(whenStart)
    }

    private fun subscribeAndFireAction(toPerform: Action) {
        Completable.fromAction(toPerform)
                .subscribeOn(targetScheduler)
                .subscribe()
    }

}
