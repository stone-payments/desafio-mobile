package io.hasegawa.presentation.screen_transactions

import io.hasegawa.presentation.screen_transactions.TransactionsContract.StateEvent
import io.hasegawa.stonesafio.domain.common.interactors.UseCase
import io.hasegawa.stonesafio.domain.payment.TransactionRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TransactionsGetUC(val transactionRepository: TransactionRepository)
    : UseCase<StateEvent, UseCase.None>() {

    override fun buildObservable(params: None?): Observable<StateEvent> {
        return transactionRepository.getTransactions()
                .subscribeOn(Schedulers.io())
                .map {
                    it.map {
                        Transaction(
                                instant = it.instant,
                                value = it.value,
                                ccLast4Digits = it.ccLast4Digits,
                                ccName = it.ccName)
                    }
                }
                .map { StateEvent.Loaded(it) as StateEvent }
                .startWith(StateEvent.Loading)
                .observeOn(AndroidSchedulers.mainThread())
    }
}