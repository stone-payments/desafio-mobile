package com.jademcosta.starstore.network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkStackBuilder {

    private static OkHttpClient clientInstance;

    private Retrofit retrofitServiceGenerator;
    private String baseUrl;

    public NetworkStackBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
        retrofitServiceGenerator = buildRetrofitServiceGenerator();
    }

    public Retrofit getServiceGenerator() {
        return retrofitServiceGenerator;
    }

    private Retrofit buildRetrofitServiceGenerator() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkhttpClient())
                .build();
    }

    private OkHttpClient buildOkhttpClient() {
        if(clientInstance == null) {
            clientInstance = new OkHttpClient.Builder().build();
        }
        return clientInstance;
    }
}
