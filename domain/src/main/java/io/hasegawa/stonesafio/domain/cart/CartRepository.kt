package io.hasegawa.stonesafio.domain.cart

import io.reactivex.Completable
import io.reactivex.Observable

interface CartRepository {
    fun getAllProducts(): Observable<List<CartProduct>>
    fun addToCart(product: CartProduct): Completable
}