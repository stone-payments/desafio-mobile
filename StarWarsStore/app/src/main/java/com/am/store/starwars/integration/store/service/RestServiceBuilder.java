package com.am.store.starwars.integration.store.service;

import com.am.store.starwars.integration.store.action.Action;

import java.lang.reflect.ParameterizedType;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Augusto on 13/01/2017.
 */
public class RestServiceBuilder {

    protected String endpoint;
    protected Retrofit retrofit;
    protected Class<?> typeClass;

    public <T> RestServiceBuilder(String endpoint, Class<T> typeClass) {
        try {
            this.endpoint = endpoint;
            this.typeClass = typeClass;
            retrofit = new Retrofit.Builder()
                    .baseUrl(endpoint)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            //TODO:
        }
    }

    public <T> T build() throws Exception {
        try {
            T service = (T) retrofit.create(typeClass);
            return service;
        } catch (Exception e) {
            throw e;
        }
    }
}