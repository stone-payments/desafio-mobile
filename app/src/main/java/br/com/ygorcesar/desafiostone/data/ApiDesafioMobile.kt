package br.com.ygorcesar.desafiostone.data

import br.com.ygorcesar.desafiostone.model.CardInformation
import br.com.ygorcesar.desafiostone.model.Item
import br.com.ygorcesar.desafiostone.model.Response
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


class ApiDesafioMobile {

    val api by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://private-615f9-desafiostone4.apiary-mock.com/")
                .build()
        retrofit.create(DesafioMobileService::class.java)
    }

    interface DesafioMobileService {

        @GET("products") fun getItems(): Observable<ArrayList<Item>>

        @POST("checkout") fun checkoutOrder(@Body cardInfo: CardInformation): Observable<Response>
    }
}
