package br.com.stone.vianna.starstore.feature.card

import br.com.stone.vianna.starstore.entity.PaymentRequest
import br.com.stone.vianna.starstore.entity.PaymentTransaction
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentApi {

    @POST("/payment/checkout")
    fun checkout(@Body paymentRequest: PaymentRequest): Observable<PaymentTransaction>
}