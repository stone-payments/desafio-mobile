package br.com.tiago.desafiomobile.service;

import br.com.tiago.desafiomobile.model.TransactionModel;

/**
 * Created by TiagoCabral on 8/23/2017.
 */

public interface TransactionService {
    rx.Observable<Void> insertTransaction(TransactionModel transactionModel);
}
