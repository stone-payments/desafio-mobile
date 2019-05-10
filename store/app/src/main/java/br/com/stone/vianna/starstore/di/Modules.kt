package  br.com.stone.vianna.starstore.di

import android.arch.persistence.room.Room
import br.com.stone.vianna.starstore.AppDatabase
import br.com.stone.vianna.starstore.BuildConfig
import br.com.stone.vianna.starstore.view.itemList.*
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val applicationModule = module(override = true) {

    single { createOkHttpClient() }
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "store_database").build() }

    single { createWebService<ItemListDataSource>(get(), BuildConfig.ITEMS_API) }
}

val repositoryModule = module {

    factory { ItemListRepositoryImpl(get()) as ItemListRepository }
}

val presenterModule = module {

    factory<ItemListContract.Presenter> { (itemView: ItemListContract.View) ->
        ItemListPresenter(itemView, get(), get())
    }
}

val daoModule = module {
    single { get<AppDatabase>().itemDao() }
    single { get<AppDatabase>().transactionDao() }
}

fun createOkHttpClient(): OkHttpClient {

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}