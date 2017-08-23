package br.com.tiago.desafiomobile.repository.remote.endpoint;

import br.com.tiago.desafiomobile.model.TransactionModel;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public interface TransactionEndPoint {

    @POST("/")
    rx.Observable<Void> insertTransaction(@Body TransactionModel transactionModel);

}
