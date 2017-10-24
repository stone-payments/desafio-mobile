package com.desafiostone.api;

import com.desafiostone.domain.Products;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Filipi Andrade on 17-Oct-17.
 */

public class ProductsAPI {

    private static final String BASE_URL = "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/";
    private static ProductsInterface mProductsInterface;

    public static ProductsInterface getClient() {
        try {
            if (mProductsInterface == null) {
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                return chain.proceed(chain.request());
                            }
                        }).build();

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                mProductsInterface = retrofit.create(ProductsInterface.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mProductsInterface;
    }

    public interface ProductsInterface {
        @GET("products.json")
        Call<ArrayList<Products>> getProducts();
    }
}
