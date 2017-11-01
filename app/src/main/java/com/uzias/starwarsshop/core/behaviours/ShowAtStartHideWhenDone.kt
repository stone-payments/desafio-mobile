package com.uzias.starwarsshop.core.behaviours

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import io.reactivex.functions.Action
import org.reactivestreams.Publisher

class ShowAtStartHideWhenDone<T>(private val whenStart: Action,
                                 private val whenDone: Action,
                                 private val targetScheduler: Scheduler)
    : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream
                .doOnSubscribe{show()}
                .doOnTerminate{hide()}
    }

    private fun show() {
        subscribeAndFireAction(whenStart)
    }

    private fun hide() {
        subscribeAndFireAction(whenDone)
    }

    private fun subscribeAndFireAction(toPerform: Action) {
        Completable.fromAction(toPerform)
                .subscribeOn(targetScheduler)
                .subscribe()
    }

}