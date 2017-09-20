package io.hasegawa.data.payment

import io.hasegawa.stonesafio.domain.payment.PaymentRequest
import io.hasegawa.stonesafio.domain.payment.PaymentResult
import io.hasegawa.stonesafio.domain.payment.PaymentService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.POST
import java.net.UnknownHostException
import java.security.InvalidParameterException

class RetrofitPaymentService(private val baseURL: String) : PaymentService {
    override fun pay(request: PaymentRequest): Single<PaymentResult> =
            Single
                    .fromCallable {
                        val retrofit = Retrofit.Builder()
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(MoshiConverterFactory.create())
                                .baseUrl(baseURL)
                                .build()

                        retrofit.create(RetrofitCall::class.java)
                    }
                    .flatMap {
                        it.makePayment().subscribeOn(Schedulers.io())
                                .map { root ->
                                    root.success?.let {
                                        PaymentResult(success = true,
                                                value = it.value,
                                                instant = it.instant,
                                                ccName = it.card_holder_name,
                                                ccLast4Digits = it.card_last_4_digits)
                                    } ?: root.error?.let {
                                        PaymentResult(success = false,
                                                value = it.value,
                                                instant = it.instant,
                                                ccName = it.card_holder_name,
                                                ccLast4Digits = it.card_last_4_digits)
                                    } ?: throw InvalidParameterException("Invalid response from service.")
                                }
                    }
                    .onErrorResumeNext { t: Throwable ->
                        when (t is UnknownHostException) {
                            true -> Single.error(PaymentService.ConnectionIssueException())
                            else -> Single.error(t)
                        }
                    }
}

private class RootJson(
        var success: SuccessJson? = null,
        var error: ErrorJson? = null)

private class SuccessJson(
        var instant: Long = -1,
        var value: Long = -1,
        var card_holder_name: String = "",
        var card_last_4_digits: String = "")

private class ErrorJson(
        val error: String = "",
        var instant: Long = -1,
        var value: Long = -1,
        var card_holder_name: String = "",
        var card_last_4_digits: String = "")

private interface RetrofitCall {
    @POST("payments")
    fun makePayment(): Single<RootJson>
}
