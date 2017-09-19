package io.hasegawa.presentation.screen_cart

import io.hasegawa.presentation.screen_cart.CartContract.StateEvent
import io.hasegawa.stonesafio.domain.cart.CartGetListUC
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers


open class CartProductsUC(val cartGetListUC: CartGetListUC) : UseCase<StateEvent, UseCase.None>() {
    override fun buildObservable(params: None?): Observable<StateEvent> {
        return cartGetListUC.buildObservable()
                .switchMap { result ->
                    when (result) {
                        is CartGetListUC.Result.Success -> {
                            val list = result.list
                            val total = list.map { it.price }.sum()

                            val events = listOf(
                                    StateEvent.SetProducts(list),
                                    StateEvent.SetCanGoToPayment(list.isNotEmpty()),
                                    StateEvent.ProductsTotal(total)
                            )
                            Observable.fromIterable(events)
                        }
                        is CartGetListUC.Result.Error -> {
                            Observable.empty<StateEvent>()
                        }
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
    }
}