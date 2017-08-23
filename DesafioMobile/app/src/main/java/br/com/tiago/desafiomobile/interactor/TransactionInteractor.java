package br.com.tiago.desafiomobile.interactor;

import java.util.List;

import br.com.tiago.desafiomobile.model.TransactionModel;
import rx.Observable;

/**
 * Created by TiagoCabral on 8/23/2017.
 */

public interface TransactionInteractor {
    Observable<Void> insertTransaction(TransactionModel transactionModel);

    Observable<List<TransactionModel>> getTransactions();
}
