package io.hasegawa.stonesafio.domain.listing

import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.hasegawa.stonesafio.domain.common.log.logd
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Lists all the items in the store.
 */
class ListItemsUC(private val service: ListingService)
    : UseCase<ListItemsUC.Result, UseCase.None>() {
    sealed class Result {
        data class Success(val list: List<ListingItemModel>) : Result()
        object ConnectionIssue : Result()
        data class Error(val error: Throwable) : Result()
    }

    override fun buildObservable(params: None?): Observable<Result> {
        return service.fetchItems()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { Result.Success(it) as Result }
                .onErrorReturn {
                    when (it) {
                        is ListingService.ConnectionIssueException -> Result.ConnectionIssue
                        else -> Result.Error(it)
                    }
                }
                .doOnNext { logd { "ListingResult: $it" } }
    }
}