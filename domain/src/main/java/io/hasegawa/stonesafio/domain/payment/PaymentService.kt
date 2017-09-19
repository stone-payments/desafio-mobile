package io.hasegawa.stonesafio.domain.payment

import io.reactivex.Single


interface PaymentService {
    class ConnectionIssueException : RuntimeException()

    fun pay(request: PaymentRequest): Single<PaymentResult>
}