package io.hasegawa.presentation.screen_listing

import io.hasegawa.presentation.screen_listing.ListingContract.Product
import io.hasegawa.stonesafio.domain.cart.CartGetListUC
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.hasegawa.stonesafio.domain.listing.ListItemsUC
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

/**
 * The main UC for listing products. It accepts a stream of Strings as parameter, where the last item
 * is used as a filter.
 *
 * This UC takes the list of products from the [io.hasegawa.stonesafio.domain.listing.ListingService],
 * then, it filters all the products by its title using the filter stream, then, it
 * takes the content from the cart to determine the products that are in the cart.
 */
open class ListingFetcherUC(val listItemsUC: ListItemsUC, val cartGetListUC: CartGetListUC)
    : UseCase<ListingFetcherUC.Result, ListingFetcherUC.Params>() {

    sealed class Result {
        data class Success(val list: List<Product>) : Result()
        object NoInternet : Result()
        data class UnknownError(val error: Throwable) : Result()
    }

    data class Params(val filterByTitleObs: Observable<String>)

    override fun buildObservable(params: Params?): Observable<ListingFetcherUC.Result> {
        requireNotNull(params)


        val filterObs = params!!.filterByTitleObs.startWith("")
                .observeOn(Schedulers.computation())
        val filterList = { list: List<Product>, filter: String ->
            when (filter) {
                "" -> list // NoOp
                else -> list.filter { it.title.contains(filter, ignoreCase = true) }
            }
        }

        val listingObs = listItemsUC.buildObservable()
                .observeOn(Schedulers.computation())
                .map {
                    when (it) {
                        is ListItemsUC.Result.Success -> it.list
                                .map {
                                    Product(
                                            id = it.id,
                                            thumbnailUrl = it.thumbnailHd,
                                            title = it.title,
                                            price = it.price,
                                            seller = it.seller,
                                            inCart = false)
                                }
                                .distinctBy { it.id }
                                .sortedBy { it.id }
                                .let { Result.Success(it) }
                        is ListItemsUC.Result.ConnectionIssue -> Result.NoInternet
                        is ListItemsUC.Result.Error -> Result.UnknownError(it.error)
                    }
                }

        val cartIdsObs = cartGetListUC.buildObservable()
                .map { result ->
                    when (result) {
                        is CartGetListUC.Result.Success -> result.list
                        else -> emptyList()
                    }
                }
                .map { it.map { it.id } }

        val preCartMap: Observable<Result> = Observable
                .combineLatest(filterObs, listingObs, BiFunction { filter, listing ->
                    when (listing) {
                        is Result.Success -> listing.copy(list = filterList(listing.list, filter))
                        else -> listing
                    }
                })

        val mapInCart = { list: List<Product>, cartIds: List<String> ->
            list.map { it.copy(inCart = cartIds.contains(it.id)) }
        }

        return Observable.combineLatest(preCartMap, cartIdsObs, BiFunction { listing, cartIds ->
            when (listing) {
                is Result.Success -> listing.copy(list = mapInCart(listing.list, cartIds))
                else -> listing
            }
        })
    }
}
