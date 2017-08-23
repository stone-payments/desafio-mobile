package br.com.tiago.desafiomobile.repository.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public class RetrofitConnection {


    private static final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    private static final HttpLoggingInterceptor.Level loggingLevel = HttpLoggingInterceptor.Level.BODY;
    public static String GET_URL = "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/";
    public static String POST_URL = "http://private-2df2e4-starstore2.apiary-mock.com";


    public static <T> T connect(Class<T> serviceClass, String url) {
        loggingInterceptor.setLevel(loggingLevel);
        httpClientBuilder.interceptors().add(loggingInterceptor);

        OkHttpClient httpClient = httpClientBuilder
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
