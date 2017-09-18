package io.hasegawa.data.cart

import io.hasegawa.stonesafio.domain.cart.CartProduct
import io.hasegawa.stonesafio.domain.cart.CartRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class InMemCartRepository : CartRepository {
    private val productsSubj = BehaviorSubject.createDefault<List<CartProduct>>(emptyList())
    private val products = mutableListOf<CartProduct>()

    override fun getAllProducts(): Observable<List<CartProduct>> = productsSubj

    override fun addToCart(product: CartProduct): Completable = Completable.fromCallable {
        if (products.firstOrNull { it.id == product.id } == null) {
            products.add(product)
            productsSubj.onNext(products + emptyList())
        }
    }
}