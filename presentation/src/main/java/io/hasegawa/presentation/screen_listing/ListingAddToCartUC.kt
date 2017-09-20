package io.hasegawa.presentation.screen_listing

import io.hasegawa.presentation.screen_listing.ListingContract.StateEvent
import io.hasegawa.stonesafio.domain.cart.CartAddUC
import io.hasegawa.stonesafio.domain.cart.CartGetListUC
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers


/**
 * Takes a product id and adds it to the cart. When the cart list changes, it will emit
 * a [StateEvent.SetNumberInCart] event with the updated number of items in the cart.
 */
open class ListingAddToCartUC(val cartAddUC: CartAddUC, val cartGetListUC: CartGetListUC)
    : UseCase<StateEvent, ListingAddToCartUC.Params>() {
    data class Params(val productIdsToAddObs: Observable<String>)

    override fun buildObservable(params: Params?): Observable<StateEvent> {
        requireNotNull(params)
        val addObs = cartAddUC.buildObservable(CartAddUC.Params(params!!.productIdsToAddObs))
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .toObservable<StateEvent>()

        val numberObs = cartGetListUC.buildObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map { result ->
                    when (result) {
                        is CartGetListUC.Result.Success -> StateEvent.SetNumberInCart(result.list.size)
                        else -> StateEvent.SetNumberInCart(0)
                    }
                }
        return Observable.merge(addObs, numberObs)
    }
}