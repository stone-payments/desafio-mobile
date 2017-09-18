package io.hasegawa.stonesafio.domain.listing

import io.reactivex.Observable

interface ListingService {
    class ConnectionIssueException : RuntimeException()


    /**
     * @throws[ConnectionIssueException] When there's any kind of internet issue.
     */
    fun fetchItems(): Observable<List<ListingItemModel>>
}
