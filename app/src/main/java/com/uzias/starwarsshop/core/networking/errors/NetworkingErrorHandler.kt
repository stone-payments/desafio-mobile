package com.uzias.starwarsshop.core.networking.errors

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkingErrorHandler<T> : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.onErrorResumeNext(Function<Throwable, Publisher<out T>> { this.handleIfNetworkingError(it) })
    }

    private fun handleIfNetworkingError(throwable: Throwable): Publisher<T> {
        if (isNetworkingError(throwable)) return asNetworkingError(throwable)
        return Flowable.error<T>(throwable)
    }

    private fun asNetworkingError(throwable: Throwable): Flowable<T> {
        val failure = failureFrom(throwable)
        return Flowable.error(NetworkingError(failure))
    }

    private fun failureFrom(throwable: Throwable): String = when {
        isConnectionTimeout(throwable) -> "Connection timeout"
        isRequestCanceled(throwable) -> "Connection interrupted error"
        noInternetAvailable(throwable) -> "No internet available"
        else -> "Unknown networking error"
    }

    private fun isNetworkingError(throwable: Throwable): Boolean {
        return isConnectionTimeout(throwable) ||
                noInternetAvailable(throwable) ||
                isRequestCanceled(throwable)
    }

    private fun isRequestCanceled(throwable: Throwable): Boolean {
        return throwable is IOException && throwable.message!!.contentEquals("Canceled")
    }

    private fun noInternetAvailable(throwable: Throwable): Boolean {
        return throwable is UnknownHostException
    }

    private fun isConnectionTimeout(throwable: Throwable): Boolean {
        return throwable is SocketTimeoutException
    }

}
