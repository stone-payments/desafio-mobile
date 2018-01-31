package br.com.valdir.desafiolojastarwars.api;

/**
 * Created by valdir on 30/01/18.
 */

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIServicePost {

    @POST("/")
    @FormUrlEncoded
    Call<Infor> savePost(@Field("id") Integer id,
                         @Field("resultado") String resultado);

    @Headers("Content-Type: application/json")
    @POST("/")
    Call<Infor> realizaPagamento(@Body RequestBody body);
}