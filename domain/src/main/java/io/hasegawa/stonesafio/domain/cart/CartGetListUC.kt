package io.hasegawa.stonesafio.domain.cart

import io.hasegawa.stonesafio.domain.common.RxUtils
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


/**
 * Fetches the list of products inside the cart. It'll emit a new list anytime the contents
 * of the cart changes.
 */
open class CartGetListUC(private val repository: CartRepository)
    : UseCase<CartGetListUC.Result, UseCase.None>() {
    sealed class Result {
        data class Success(val list: List<CartProduct>) : Result()
        data class Error(val error: Throwable) : Result()
    }

    override fun buildObservable(params: None?): Observable<Result> {
        val errorsSubj = PublishSubject.create<Result>()

        val getObs = repository.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { Result.Success(it) as Result }
                .retryWhen { errorObs ->
                    errorObs.flatMap { error ->
                        RxUtils.backoff()
                                .doOnNext { errorsSubj.onNext(Result.Error(error)) }
                    }
                }

        return Observable.merge(getObs, errorsSubj)
    }
}