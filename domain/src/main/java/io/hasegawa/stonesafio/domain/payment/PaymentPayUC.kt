package io.hasegawa.stonesafio.domain.payment

import io.hasegawa.stonesafio.domain.cc.CCModel
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Takes a [PaymentPayUC.Params.value] and a credit card and make a payment request.
 * If the request succeeds, then a transaction is saved into the [TransactionRepository].
 */
class PaymentPayUC(val paymentService: PaymentService, val transactionRepository: TransactionRepository)
    : UseCase<PaymentResult, PaymentPayUC.Params>() {
    data class Params(val value: Long, val ccModel: CCModel)

    override fun buildObservable(params: Params?): Observable<PaymentResult> {
        requireNotNull(params)

        val ccModel = params!!.ccModel
        val value = params.value
        return Observable
                .fromCallable {
                    PaymentRequest(ccName = ccModel.number,
                            ccNumber = ccModel.number,
                            ccCVV = ccModel.cvv,
                            ccExpDate = "${ccModel.expDateMonth}/${ccModel.expDateYear}",
                            value = value)
                }
                .flatMapSingle { it -> paymentService.pay(it).subscribeOn(Schedulers.io()) }
                .flatMapSingle { result ->
                    when (result.success) {
                        true ->
                            TransactionModel(
                                    value = result.value,
                                    instant = result.instant,
                                    ccLast4Digits = result.ccLast4Digits,
                                    ccName = result.ccName)
                                    .let { model ->
                                        transactionRepository.insert(model)
                                                .subscribeOn(Schedulers.io())
                                                .toSingle { result }
                                    }
                        else -> Single.just(result)
                    }
                }
    }
}