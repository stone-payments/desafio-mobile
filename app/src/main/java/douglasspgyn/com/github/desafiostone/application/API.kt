package douglasspgyn.com.github.desafiostone.application

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Douglas on 12/11/17.
 */

object API {

    private var retrofitAPI: Retrofit = Retrofit.Builder()
            .baseUrl(ApplicationConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    private var retrofitApiary: Retrofit = Retrofit.Builder()
            .baseUrl(ApplicationConfig.APIARY_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    fun <T> provideAPIService(serviceClass: Class<T>): T {
        return retrofitAPI.create(serviceClass)
    }

    fun <T> provideApiaryService(serviceClass: Class<T>): T {
        return retrofitApiary.create(serviceClass)
    }
}