package douglasspgyn.com.github.desafiostone.application

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Douglas on 12/11/17.
 */

object API {

    private var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ApplicationConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    fun <T> provideService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}