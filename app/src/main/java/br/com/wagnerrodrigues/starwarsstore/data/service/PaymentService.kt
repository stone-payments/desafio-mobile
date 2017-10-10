package br.com.wagnerrodrigues.starwarsstore.data.service

import br.com.wagnerrodrigues.starwarsstore.domain.entity.PaymentResponse
import br.com.wagnerrodrigues.starwarsstore.domain.entity.Transaction
import br.com.wagnerrodrigues.starwarsstore.domain.event.PaymentSuccessfulEvent
import br.com.wagnerrodrigues.starwarsstore.domain.event.PaymentUnsuccessfulEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentService : RestService() {
    fun sendPaymentData(transaction : Transaction){
        val jsonCall : Call<PaymentResponse> = serviceBuilder.create(API::class.java).sendPaymentData(transaction)
        jsonCall.enqueue(object : Callback<PaymentResponse> {
            override fun onResponse(call: Call<PaymentResponse>?, response: Response<PaymentResponse>?) {
                if(response!!.isSuccessful && response.body()?.status == "sucesso"){
                    EventBus.getDefault().post(PaymentSuccessfulEvent(transaction))
                }else{
                    EventBus.getDefault().post(PaymentUnsuccessfulEvent(response.body()?.message))
                }

            }

            override fun onFailure(call: Call<PaymentResponse>?, t: Throwable?) {
                EventBus.getDefault().post(PaymentUnsuccessfulEvent("Erro ao se comunicar com o servidor. Tente novamente mais tarde."))
            }

        })
    }
}