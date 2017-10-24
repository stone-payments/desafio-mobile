package com.desafiostone.api;

import com.desafiostone.domain.Purchase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

/**
 * Created by Filipi Andrade on 19-Oct-17.
 */

public class APIAry {

        private static final String BASE_URL = "https://private-8c29f-purchase4.apiary-mock.com/";

    private static APIAryInterface mAPIAryInterface;

    public static APIAryInterface getClient() {
        try {
            if (mAPIAryInterface == null) {
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

                mAPIAryInterface = retrofit.create(APIAryInterface.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mAPIAryInterface;
    }

    public interface APIAryInterface {
        @POST("purchase")
        Call<Purchase> finishPurchase();
    }
}
