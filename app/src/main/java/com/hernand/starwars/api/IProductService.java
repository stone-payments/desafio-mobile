package com.hernand.starwars.api;

import com.hernand.starwars.vo.ProductVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Nando on 27/05/2017.
 */

public interface IProductService {
    public final String API_URL = "https://private-96027-starwars4.apiary-mock.com/";
    @GET("products")
    Call<List<ProductVO>> listProducts();
}
