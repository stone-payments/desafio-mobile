package io.hasegawa.stonesafio.domain.payment

import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class PaymentGetTransactionsUC(val paymentService: PaymentService)
: UseCase<List<PaymentResult>, UseCase.None>() {
    override fun buildObservable(params: None?): Observable<List<PaymentResult>> =
            paymentService.getAllResults()
                    .subscribeOn(Schedulers.io())
}