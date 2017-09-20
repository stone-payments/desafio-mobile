package io.hasegawa.presentation.screen_cart

import io.hasegawa.presentation.screen_cart.CartContract.StateEvent
import io.hasegawa.stonesafio.domain.cart.CartRepository
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Sends an event to the [CartRepository] asking for the removal of the product from the cart.
 * This UC will not return anything meaningful.
 */
class CartRemoveItemUC(val cartRepository: CartRepository)
    : UseCase<StateEvent, Observable<String>>() {

    /**
     * @param[params] A stream of product ids to remove from the cart.
     */
    override fun buildObservable(params: Observable<String>?): Observable<StateEvent> {
        requireNotNull(params)
        return params!!
                .concatMap { id ->
                    cartRepository.removeFromCart(id)
                            .subscribeOn(Schedulers.io())
                            .onErrorComplete()
                            .toObservable<Unit>()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .toObservable()
    }
}