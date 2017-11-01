package com.uzias.starwarsshop.core.behaviours.refreshtooglestate

import com.uzias.starwarsshop.core.errors.ContentNotFoundError
import com.uzias.starwarsshop.core.presentation.ToogleRefreshView
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import io.reactivex.functions.Action
import org.reactivestreams.Publisher

open class RefreshToogleCoordination<T>(private val view: ToogleRefreshView, private val uiScheduler: Scheduler):
        FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.doOnSubscribe({fireAction(view.disableRefresh())})
                .doOnError(this::enableForSpecialErrorCase)
                .doOnComplete({fireAction(view.enableRefresh())})
    }

    private fun enableForSpecialErrorCase(throwable: Throwable) {
        if (throwable is ContentNotFoundError) fireAction(view.enableRefresh())
    }

    private fun fireAction(toPerform: Action) {
        Completable.fromAction(toPerform)
                .subscribeOn(uiScheduler)
                .subscribe()
    }

}
