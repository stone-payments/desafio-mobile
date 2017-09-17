package io.hasegawa.stonesafio.domain.common.interactors

import io.reactivex.Observable

/**
 * This class is based on the UseCase from:
 *   https://github.com/android10/Android-CleanArchitecture-Kotlin
 */
abstract class UseCase<T, in P> where P : Any {
    abstract fun buildObservable(params: P? = null): Observable<T>
    class None
}