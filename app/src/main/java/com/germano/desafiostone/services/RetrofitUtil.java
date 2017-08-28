package com.germano.desafiostone.services;

import com.germano.desafiostone.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by germano on 27/08/17.
 */

public final class RetrofitUtil {

    public static RetrofitUtil instance;

    private RetrofitUtil() {
    }

    public static RetrofitUtil getInstance() {
        if (instance == null) {
            instance = new RetrofitUtil();
        }
        return instance;
    }

    public Retrofit apiBuild() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    public Retrofit apiaryBuild() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_APIARY)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}