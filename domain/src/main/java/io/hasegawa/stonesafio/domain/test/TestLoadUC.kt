package io.hasegawa.stonesafio.domain.test

import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.reactivex.Observable


class TestLoadUC(private val testRepository: TestRepository) : UseCase<String, UseCase.None>() {
    override fun buildObservable(params: None?): Observable<String> {
        return testRepository.getTestText()
                .map { it.toUpperCase() }
    }
}