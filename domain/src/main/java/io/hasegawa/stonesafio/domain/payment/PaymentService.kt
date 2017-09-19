package io.hasegawa.stonesafio.domain.payment

import io.reactivex.Observable
import io.reactivex.Single


interface PaymentService {
    fun pay(request: PaymentRequest): Single<PaymentResult>

    fun getAllResults(): Observable<List<PaymentResult>>
}