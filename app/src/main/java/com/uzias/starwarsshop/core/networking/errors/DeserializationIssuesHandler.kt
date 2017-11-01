package com.uzias.starwarsshop.core.networking.errors

import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.uzias.starwarsshop.core.errors.UnexpectedResponseError
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Function
import org.reactivestreams.Publisher

class DeserializationIssuesHandler<T> : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.onErrorResumeNext(Function<Throwable, Publisher<out T>> { this.handleErrorFromDeserializer(it) })
    }

    private fun handleErrorFromDeserializer(throwable: Throwable): Publisher<T> {
        if (isDeserializationError(throwable)) {
            return Flowable.error(UnexpectedResponseError("Deserialization Error"))
        }

        return Flowable.error<T>(throwable)
    }

    private fun isDeserializationError(throwable: Throwable): Boolean {
        return throwable is IllegalStateException
                || throwable is JsonIOException
                || throwable is JsonSyntaxException
                || throwable is JsonParseException
    }
}
