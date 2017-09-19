package io.hasegawa.data.listing

import io.hasegawa.stonesafio.domain.listing.ListingItemModel
import io.hasegawa.stonesafio.domain.listing.ListingService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class RetrofitListingService(private val baseURL: String) : ListingService {
    override fun fetchItems(): Observable<List<ListingItemModel>> =
            Observable
                    .fromCallable {
                        val retrofit = Retrofit.Builder()
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(MoshiConverterFactory.create())
                                .baseUrl(baseURL)
                                .build()

                        retrofit.create(RetrofitCall::class.java)
                    }
                    .flatMap {
                        it.getItems().subscribeOn(Schedulers.io())
                                .map { list ->
                                    list.mapIndexed { idx, item ->
                                        ListingItemModel(
                                                id = "item$idx",
                                                title = item.title,
                                                price = item.price,
                                                zipcode = item.zipcode,
                                                seller = item.seller,
                                                thumbnailHd = item.thumbnailHd,
                                                date = item.date)
                                    }
                                }
                                .toObservable()
                    }
}

private class ItemJson(
        var title: String = "",
        var price: Long = -1,
        var zipcode: String = "",
        var seller: String = "",
        var thumbnailHd: String = "",
        var date: String = "")

private interface RetrofitCall {
    @GET("products.json")
    fun getItems(): Single<List<ItemJson>>
}