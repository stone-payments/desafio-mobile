package jb.project.starwarsshoppinglist.service

import io.reactivex.Observable
import jb.project.starwarsshoppinglist.BuildConfig
import jb.project.starwarsshoppinglist.model.Product
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by Jb on 12/10/2017.
 */
interface ProductListService {


    @GET("products")
    fun fetchProductList(): Observable<Response<MutableList<Product>>>

    companion object Factory {
        fun create(): ProductListService {
            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(createClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://private-65b3f2-joaobaptista.apiary-mock.com/")
                    .build()

            return retrofit.create(ProductListService::class.java)
        }
        private fun createClient(): OkHttpClient {
            val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                okHttpClientBuilder.addInterceptor(loggingInterceptor)
            }
            return okHttpClientBuilder.build()
        }
    }
}
