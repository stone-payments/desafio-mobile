package io.hasegawa.presentation.screen_cart

import io.hasegawa.presentation.screen_cart.CartContract.StateEvent
import io.hasegawa.stonesafio.domain.cart.CartClearUC
import io.hasegawa.stonesafio.domain.cc.CCModel
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.hasegawa.stonesafio.domain.common.log.logd
import io.hasegawa.stonesafio.domain.payment.PaymentPayUC
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers


/**
 * Takes a stream of value and credit card info, and makes a payment request.
 * If the payment succeeds, then it will clear the cart and show a success page.
 */
class CartPayUC(val paymentPayUC: PaymentPayUC, val cartClearUC: CartClearUC)
    : UseCase<StateEvent, CartPayUC.Params>() {
    data class Params(val value: Long, val ccInfo: CartCCInfo)

    override fun buildObservable(params: Params?): Observable<StateEvent> {
        requireNotNull(params)

        val value = params!!.value
        val ccInfo = params.ccInfo
        val ccModel = try {
            CCModel(number = ccInfo.number,
                    name = ccInfo.name,
                    cvv = ccInfo.cvv.toInt(),
                    expDateMonth = ccInfo.expDateMonth.toInt(),
                    expDateYear = ccInfo.expDateYear.toInt())
        } catch (e: Exception) {
            null
        }

        val resultObs = when (ccModel) {
            null -> Observable.just(CartPaymentResult(false, value, -1))
            else -> paymentPayUC.buildObservable(PaymentPayUC.Params(value, ccModel))
                    .map { result ->
                        CartPaymentResult(
                                success = result.success,
                                value = result.value,
                                instant = result.instant)
                    }
        }

        return resultObs
                .map { result ->
                    when (result.success) {
                        true -> StateEvent.SummarySuccess(result.value)
                        else -> StateEvent.ShowSummaryError
                    }
                }
                .flatMap { result ->
                    if (result is StateEvent.SummarySuccess) {
                        cartClearUC.buildObservable()
                                .flatMap { Observable.just(result) }
                    } else {
                        Observable.just(result)
                    }
                }
                .startWith(StateEvent.ShowLoadingSummary)
                .doOnNext { logd { "Payment results: $it" } }
                .observeOn(AndroidSchedulers.mainThread())
    }
}
