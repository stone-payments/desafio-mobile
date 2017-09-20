package io.hasegawa.stonesafio.domain.test

import io.reactivex.Observable

interface TestRepository {
    fun getTestText(): Observable<String>
}