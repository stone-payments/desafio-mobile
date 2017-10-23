package com.stone.mobile.stonestore.Utils;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class Conexao {

    public final static String URL_API = "http://private-b91eb3-lucianogsilva.apiary-mock.com/";
    public final static String URL_PRODUCTS = "products";
    public final static String URL_SHOPPING = "shopping";
    private static final int CODE_OK = 200;
    private static final String LOG_TAG = "DEBUG_LOG";


    public static List<Produto> buscarProdutos() {

        List<Produto> produtos = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServicesInterface service = retrofit.create(APIServicesInterface.class);

        Call<List<Produto>> call = service.getProdutos();
        try {
            Response<List<Produto>> listResponse = call.execute();

            Log.i(LOG_TAG, "ListaProdutos->Response: " + listResponse);
            Log.i(LOG_TAG, "ListaProdutos->Response.successfull: " + listResponse.isSuccessful());
            Log.i(LOG_TAG, "ListaProdutos->Response.code: " + listResponse.code());

            if (listResponse.code() == CODE_OK) {
                produtos = listResponse.body();
            }

        } catch (IOException e) {
            Log.i(LOG_TAG, "ListaProdutos->Error:" + e.getMessage());
            Log.i(LOG_TAG, "ListaProdutos->Error:" + e.toString());
        }

        return produtos;
    }

    public static void makeShopping(TransacaoVenda venda) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServicesInterface service = retrofit.create(APIServicesInterface.class);

        Call<ResponseBody> call = service.makeShopping(venda);
        try {
            Response<ResponseBody> responseBody = call.execute();

            Log.i(LOG_TAG, "Shopping->Response: " + responseBody);
            Log.i(LOG_TAG, "Shopping->Response.successfull: " + responseBody.isSuccessful());
            Log.i(LOG_TAG, "Shopping->Response.code: " + responseBody.code());

        } catch (IOException e) {
            Log.i(LOG_TAG, "Shopping->Error:" + e.getMessage());
            Log.i(LOG_TAG, "Shopping->Error:" + e.toString());
        }

    }

    public interface APIServicesInterface {

        @GET(Conexao.URL_PRODUCTS)
        Call<List<Produto>> getProdutos();

        @POST(Conexao.URL_SHOPPING)
        Call<ResponseBody> makeShopping(@Body TransacaoVenda venda);
    }

}
