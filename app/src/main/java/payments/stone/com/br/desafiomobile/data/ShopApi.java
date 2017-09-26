package payments.stone.com.br.desafiomobile.data;

import java.util.List;

import payments.stone.com.br.desafiomobile.model.Order;
import payments.stone.com.br.desafiomobile.model.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by william.gouvea on 9/25/17.
 */

public interface ShopApi {
    @GET("products")
    Call<List<Product>> getProducts();


    @POST("checkout")
    Call<Order> checkout(@Body Order order);

}
