package payments.stone.com.br.desafiomobile.home;


import java.util.List;

import payments.stone.com.br.desafiomobile.ShopitApplication;
import payments.stone.com.br.desafiomobile.data.ProductsResponse;
import payments.stone.com.br.desafiomobile.data.ShopApi;
import payments.stone.com.br.desafiomobile.model.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by william.gouvea on 9/21/17.
 */

public class HomePresenter {
    private HomeView mView;
//    private ProductService mService;

    private ShopApi mApi;


    public HomePresenter(HomeView mView) {
        this.mView = mView;
        mApi = ShopitApplication.getInstance().provideApi();
    }

    public HomePresenter loadProducts() {
        mView.showLoading();


        Call<List<Product>> call =  mApi.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    onProductsReceived(new ProductsResponse(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                    onProductsError(t.getMessage());
            }
        });

//        new HomeActivity.ProductAsyncTask(this, mView.context().getApplicationContext()).execute();
        return this;
    }

    public void onProductsReceived(ProductsResponse response) {
        mView.hideLoading();
        mView.showProducts(response.productList);
    }

    public void onProductsError(String error){
        mView.hideLoading();
        mView.showError(error);
    }

    public void resume() {

    }

    public void pause() {

    }

    public void create() {

    }

    public void destroy() {

    }
}
