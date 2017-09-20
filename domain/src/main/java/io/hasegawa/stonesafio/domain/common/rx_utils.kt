package io.hasegawa.stonesafio.domain.common

import io.reactivex.Observable
import java.util.concurrent.TimeUnit


object RxUtils {
    fun backoff(numTries: Int = Int.MAX_VALUE, initialDelayMs: Long = 1000L,
                multiplier: Double = 1.0, maxIntervalMs: Long = 30L * 60L * 1000L): Observable<Long> {
        val tries = Observable.range(0, numTries)
        val interval = { idx: Int ->
            val backoff = Math.pow(2.0, idx.toDouble()) * multiplier
            val timer = (initialDelayMs + backoff).toLong()
                    .coerceAtMost(maxIntervalMs)
            Observable.timer(timer, TimeUnit.MILLISECONDS)
        }
        return tries.concatMap(interval)
    }
}