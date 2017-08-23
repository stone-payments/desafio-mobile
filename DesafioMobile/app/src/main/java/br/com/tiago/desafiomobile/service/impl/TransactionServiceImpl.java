package br.com.tiago.desafiomobile.service.impl;

import br.com.tiago.desafiomobile.model.TransactionModel;
import br.com.tiago.desafiomobile.repository.remote.RetrofitConnection;
import br.com.tiago.desafiomobile.repository.remote.endpoint.TransactionEndPoint;
import br.com.tiago.desafiomobile.service.TransactionService;
import retrofit2.http.Body;
import rx.Observable;

/**
 * Created by TiagoCabral on 8/23/2017.
 */

public class TransactionServiceImpl implements TransactionService {
    @Override
    public Observable<Void> insertTransaction(@Body TransactionModel transactionModel) {
        return RetrofitConnection.connect(TransactionEndPoint.class, RetrofitConnection.POST_URL).insertTransaction(transactionModel);
    }
}
