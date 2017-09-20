package io.hasegawa.data.listing

import io.hasegawa.stonesafio.domain.listing.ListingItemModel
import io.hasegawa.stonesafio.domain.listing.ListingService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class RetrofitListingService(private val baseURL: String) : ListingService {
    private val cache = BehaviorSubject.create<List<ListingItemModel>>()

    override fun fetchItems(): Observable<List<ListingItemModel>> =
            cache.timeout(50, TimeUnit.MILLISECONDS, Observable.empty())
                    .take(1)
                    .concatWith(
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
                                    .doOnNext { cache.onNext(it) }
                                    .onErrorResumeNext { t: Throwable ->
                                        when (t is UnknownHostException) {
                                            true -> Observable.error(ListingService.ConnectionIssueException())
                                            else -> Observable.error(t)
                                        }
                                    }
                    )
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