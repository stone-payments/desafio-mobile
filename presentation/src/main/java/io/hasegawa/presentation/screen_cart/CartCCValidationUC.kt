package io.hasegawa.presentation.screen_cart

import io.hasegawa.presentation.screen_cart.CartContract.StateEvent.SetCCInfo
import io.hasegawa.presentation.screen_cart.CartContract.StateEvent.SetCanConfirmPayment
import io.hasegawa.stonesafio.domain.cc.CCModel
import io.hasegawa.stonesafio.domain.cc.CCValidatorDevice
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers

private typealias ObsStr = Observable<String>

/**
 * Takes stream of changes from the CC fields, validates them,
 * and then emits a [CartContract.StateEvent.SetCCInfo] event.
 */
open class CartCCValidationUC(private val ccValidatorDevice: CCValidatorDevice)
    : UseCase<CartContract.StateEvent, CartCCValidationUC.Params>() {
    data class Params(val number: ObsStr, val name: ObsStr, val cvv: ObsStr,
                      val expDateMonth: ObsStr, val expDateYear: ObsStr)

    override fun buildObservable(params: Params?): Observable<CartContract.StateEvent> {
        requireNotNull(params)
        fun emptyStart(obs: ObsStr) = obs.startWith("").observeOn(Schedulers.computation())

        val numberObs = emptyStart(params!!.number)
        val nameObs = emptyStart(params.name)
        val cvvObs = emptyStart(params.cvv)
        val expDateMonthObs = emptyStart(params.expDateMonth)
        val expDateYearObs = emptyStart(params.expDateYear)

        val ccInfoObs: Observable<CartCCInfo> = Observable
                .combineLatest(numberObs, nameObs, cvvObs, expDateMonthObs, expDateYearObs,
                        Function5 { number, name, cvv, month, year ->
                            CartCCInfo(
                                    number = number,
                                    name = name,
                                    cvv = cvv,
                                    expDateMonth = month,
                                    expDateYear = year)
                        })

        return ccInfoObs
                .map { info ->
                    val model = CCModel(
                            number = info.number,
                            name = info.name,
                            cvv = info.cvv.toIntOrNull() ?: -1,
                            expDateMonth = info.expDateMonth.toIntOrNull() ?: -1,
                            expDateYear = info.expDateYear.toIntOrNull() ?: -1)
                    val validation = ccValidatorDevice.isCardValid(model)

                    info.copy(
                            numberValid = validation.number,
                            nameValid = validation.name,
                            cvvValid = validation.cvv,
                            expDateMonthValid = validation.expDateMonth,
                            expDateYearValid = validation.expDateYear)
                }
                .map { newInfo -> listOf(SetCCInfo(newInfo), SetCanConfirmPayment(newInfo.valid)) }
                .flatMapIterable { it }
                .cast(CartContract.StateEvent::class.java)
                .observeOn(AndroidSchedulers.mainThread())
    }
}
