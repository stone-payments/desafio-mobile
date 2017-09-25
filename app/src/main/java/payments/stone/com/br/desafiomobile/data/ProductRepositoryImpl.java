package payments.stone.com.br.desafiomobile.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import payments.stone.com.br.desafiomobile.commons.Bus;
import payments.stone.com.br.desafiomobile.model.Product;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.gouvea on 9/25/17.
 */

public class ProductRepositoryImpl implements ProductsRepository {

    List<Product> localData;


    public ProductRepositoryImpl() {
        this.localData = new ArrayList<>();



    }

    @Override
    public void loadProducts() {
        if (localData != null && !localData.isEmpty()) {
            Bus.getInstance().post();
            return;
        }


    }
}
