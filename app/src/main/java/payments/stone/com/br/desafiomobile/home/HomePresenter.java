package payments.stone.com.br.desafiomobile.home;


import payments.stone.com.br.desafiomobile.cart.ProductsResponse;

/**
 * Created by william.gouvea on 9/21/17.
 */

public class HomePresenter {
    private HomeView mView;
//    private ProductService mService;


    public HomePresenter(HomeView mView) {
        this.mView = mView;
    }

    public HomePresenter loadProducts() {
        mView.showLoading();
        new HomeActivity.ProductAsyncTask(this, mView.context().getApplicationContext()).execute();
        return this;
    }

    public void resume() {

    }

    public void pause() {

    }

    public void create() {

    }

    public void destroy() {

    }

    public void onProductsReceived(ProductsResponse response) {
        mView.hideLoading();
        mView.showProducts(response.productList);
    }
}
