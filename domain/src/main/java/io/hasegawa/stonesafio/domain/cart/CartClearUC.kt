package io.hasegawa.stonesafio.domain.cart

import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CartClearUC(val cartRepository: CartRepository) : UseCase<Boolean, UseCase.None>() {
    override fun buildObservable(params: None?): Observable<Boolean> {
        return cartRepository.clear()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .toSingle { true }
                .onErrorReturnItem(false)
                .toObservable()
    }
}