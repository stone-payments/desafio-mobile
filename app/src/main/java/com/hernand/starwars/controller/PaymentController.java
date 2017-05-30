package com.hernand.starwars.controller;

import android.content.Context;
import android.widget.EditText;

import com.hernand.starwars.api.IPaymentService;
import com.hernand.starwars.model.dao.TransactDao;
import com.hernand.starwars.vo.ProductVO;
import com.hernand.starwars.vo.TransactionVO;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nando on 29/05/2017.
 */

public class PaymentController {

    private Context context;
    private TransactDao transactDao;
    public PaymentController(Context context){
        this.context = context;
        transactDao = new TransactDao();
    }

    public void saveTransaction(TransactionVO transactionVO) {
        transactDao.saveTransaction(transactionVO);
    }

    public void chamadaApiRestPagamento(TransactionVO transactionVO,final Callback<TransactionVO> callback) {
        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IPaymentService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        IPaymentService service = retrofit.create(IPaymentService.class);
        /*

        {
           "card_number":"1234123412341234",
           "value":7990,
           "cvv":789,
           "card_holder_name":"Luke Skywalker",
           "exp_date":"12/24"
        }
         */
        Call<TransactionVO> callPayment = service.doPayment(transactionVO);

        callPayment.enqueue(new Callback<TransactionVO>() {
            @Override
            public void onResponse(Call<TransactionVO> call, Response<TransactionVO> response) {
                callback.onResponse(call,response);

            }

            @Override
            public void onFailure(Call<TransactionVO> listCall, Throwable t) {
                callback.onFailure(listCall,t);

            }
        });
    }

    public Long getTotal(List<ProductVO> listaProdutos) {
        Long valor = 0L;
        for(ProductVO p:listaProdutos){
            valor += p.getPrice();
        }
        return valor;
    }


    public String getExpirationMonthYear(EditText etMesCartao, EditText etAnoCartao) {

        StringBuilder sb = new StringBuilder();

        if(!etMesCartao.getText().toString().isEmpty() && !etAnoCartao.getText().toString().isEmpty()) {
            sb.append(etAnoCartao.getText().toString()).append("/").append(etAnoCartao.getText().toString());
        }

        return  sb.toString();
    }
}
