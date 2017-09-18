package io.hasegawa.data.listing

import io.hasegawa.stonesafio.domain.listing.ListingItemModel
import io.hasegawa.stonesafio.domain.listing.ListingService
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class InMemListingService : ListingService {
    val listingSubject = BehaviorSubject.createDefault(defaultListing)

    override fun fetchItems(): Observable<List<ListingItemModel>> = listingSubject
}

private val defaultListing = listOf(
        ListingItemModel(
                id = "p0",
                title = "Sabre de luz",
                price = 150_000,
                zipcode = "13537-000",
                seller = "Mario Mota",
                thumbnailHd = "http://www.obrigadopelospeixes.com/wp-content/uploads/2015/12/kalippe_lightsaber_by_jnetrocks-d4dyzpo1-1024x600.jpg",
                date = "20/11/2015"),
        ListingItemModel(
                id = "p1",
                title = "Capacete de StormTrooper",
                price = 30_000,
                zipcode = "21212-121",
                seller = "Edu Guimar\u00e3es",
                thumbnailHd = "https://cmkt-image-prd.global.ssl.fastly.net/0.1.0/ps/760104/300/200/m1/fpnw/wm0/clean_tt_v001.0001-.png?1446786887&s=dcba72825ebb6982f69cd9aeeddcf9ca",
                date = "25/10/2015")
)
