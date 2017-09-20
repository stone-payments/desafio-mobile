package io.hasegawa.data.test

import io.hasegawa.stonesafio.domain.test.TestRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class InMemTestRepository : TestRepository {
    override fun getTestText(): Observable<String> {
        return Observable.just("Test String!")
                .delay(5000, TimeUnit.MILLISECONDS)
    }
}
