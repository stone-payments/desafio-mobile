package br.com.valdir.desafiolojastarwars.api;

/**
 * Created by valdy on 30/01/2018.
 */

public class ApiUtils{

    public static final String BASE_URL = "https://private-614fc1-pagamentoapi3557.apiary-mock.com/";

    public static APIServicePost getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIServicePost.class);
    }
}