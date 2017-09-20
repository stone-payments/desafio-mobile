package io.hasegawa.data.payment

import io.hasegawa.stonesafio.domain.payment.PaymentRequest
import io.hasegawa.stonesafio.domain.payment.PaymentResult
import io.hasegawa.stonesafio.domain.payment.PaymentService
import io.reactivex.Single
import java.util.concurrent.TimeUnit


class InMemPaymentService : PaymentService {
    override fun pay(request: PaymentRequest): Single<PaymentResult> =
            Single
                    .fromCallable {
                        val now = System.currentTimeMillis()
                        PaymentResult(success = true,
                                value = request.value,
                                instant = now,
                                ccLast4Digits = request.ccNumber.takeLast(4),
                                ccName = request.ccName)
                    }
                    .delay(2000, TimeUnit.MILLISECONDS)
}