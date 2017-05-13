package com.jademcosta.starstore.network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkStackBuilder {

    private static NetworkStackBuilder instance;

    private Retrofit retrofitServiceGenerator;

    private NetworkStackBuilder(){
        retrofitServiceGenerator = buildRetrofitServiceGenerator();
    }

    public static NetworkStackBuilder getInstance() {
        if(instance == null) {
            instance = new NetworkStackBuilder();
        }
        return instance;
    }

    public Retrofit getServiceGenerator() {
        return retrofitServiceGenerator;
    }

    private Retrofit buildRetrofitServiceGenerator(){
        return new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkhttpClient())
                .build();
    }

    private OkHttpClient buildOkhttpClient() {
        return new OkHttpClient.Builder().build();
    }
}
