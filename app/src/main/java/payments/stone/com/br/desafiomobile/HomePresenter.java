package payments.stone.com.br.desafiomobile;

/**
 * Created by william.gouvea on 9/21/17.
 */

public class HomePresenter {
    private HomeView mView;
//    private ProductService mService;


    public HomePresenter(HomeView mView) {
        this.mView = mView;
    }

    public void loadProducts(){
        mView.showLoading();
    }

    public void resume(){

    }

    public void pause(){

    }

    public void create(){

    }

    public void destroy(){

    }

    public void onProductsReceived(ProductsResponse response){
        mView.hideLoading();
        mView.showProducts(response.productList);
    }
}
