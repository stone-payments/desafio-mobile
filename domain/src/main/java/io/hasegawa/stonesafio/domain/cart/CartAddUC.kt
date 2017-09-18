package io.hasegawa.stonesafio.domain.cart

import io.hasegawa.stonesafio.domain.common.RxUtils
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.hasegawa.stonesafio.domain.common.toKtType
import io.hasegawa.stonesafio.domain.common.toOptional
import io.hasegawa.stonesafio.domain.listing.ListItemsUC
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * Takes the product id as input, fetches the product from the
 * [io.hasegawa.stonesafio.domain.listing.ListingService] and add it to the [CartRepository].
 */
class CartAddUC(val listItemsUC: ListItemsUC, val cartRepository: CartRepository)
    : UseCase<CartAddUC.Result, CartAddUC.Params>() {
    data class Params(val productIdAddObs: Observable<String>)

    sealed class Result {
        data class Added(val product: CartProduct) : Result()
        data class ProductNotFound(val id: String) : Result()
        data class Error(val error: Throwable) : Result()
    }

    override fun buildObservable(params: Params?): Observable<Result> {
        requireNotNull(params)
        val errorsSubj = PublishSubject.create<Result>()
        val productObs = params!!.productIdAddObs
                .concatMap { id ->
                    listItemsUC.buildObservable()
                            .take(1)
                            .map {
                                (it as? ListItemsUC.Result.Success)
                                        ?.list
                                        ?.firstOrNull { it.id == id }
                                        .toOptional()
                            }
                            .concatMap { productMaybe ->
                                productMaybe.toKtType()
                                        ?.let { product ->
                                            val cartProduct = product.run {
                                                CartProduct(
                                                        id = id,
                                                        title = title,
                                                        thumbnailUrl = thumbnailHd,
                                                        price = price,
                                                        instantAdded = System.currentTimeMillis()
                                                )
                                            }
                                            cartRepository.addToCart(cartProduct)
                                                    .toSingleDefault(cartProduct)
                                                    .toObservable()
                                                    .map { Result.Added(it) }
                                        }
                                        ?: Observable.just(Result.ProductNotFound(id))
                            }
                            .retryWhen { errorObs ->
                                errorObs.flatMap { error ->
                                    RxUtils.backoff()
                                            .doOnNext { errorsSubj.onNext(Result.Error(error)) }
                                }
                            }
                }

        return Observable.merge(productObs, errorsSubj)
    }
}