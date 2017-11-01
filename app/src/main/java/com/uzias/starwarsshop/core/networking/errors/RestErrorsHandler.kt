package com.uzias.starwarsshop.core.networking.errors

import com.uzias.starwarsshop.core.errors.ContentNotFoundError
import com.uzias.starwarsshop.core.errors.UnexpectedResponseError
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import retrofit2.HttpException

class RestErrorsHandler<T> : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.onErrorResumeNext(Function<Throwable, Publisher<out T>> { this.handleIfRestError(it) })
    }

    private fun handleIfRestError(throwable: Throwable): Publisher<T> {

        if (notFound(throwable)) {
            return Flowable.error(ContentNotFoundError())
        }

        if (otherRestError(throwable)) {
            return Flowable.error(UnexpectedResponseError("Undesired response for this call"))
        }

        return Flowable.error<T>(throwable)
    }

    private fun otherRestError(throwable: Throwable): Boolean {
        return !notFound(throwable) && throwable is HttpException
    }

    private fun notFound(throwable: Throwable): Boolean {

        if (throwable is HttpException) {
            return throwable.code() == 404
        }

        return false
    }
}
