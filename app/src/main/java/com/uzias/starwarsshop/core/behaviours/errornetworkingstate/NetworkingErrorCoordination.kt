package com.uzias.starwarsshop.core.behaviours.errornetworkingstate

import com.uzias.starwarsshop.core.networking.errors.NetworkingError
import com.uzias.starwarsshop.core.presentation.NetworkingView
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import org.reactivestreams.Publisher

class NetworkingErrorCoordination<T>(private val view: NetworkingView, private val uiScheduler: Scheduler) : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.doOnError({ this.handleIfNetworkingError(it) })
    }

    private fun handleIfNetworkingError(throwable: Throwable): Publisher<T> {
        if (throwable is NetworkingError) {
            Completable.fromAction(view.networkingErrorState())
                    .subscribeOn(uiScheduler)
                    .subscribe()
        }

        return Flowable.error<T>(throwable)
    }

}
