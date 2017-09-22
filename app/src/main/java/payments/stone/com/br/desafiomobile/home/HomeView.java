package payments.stone.com.br.desafiomobile.home;

import java.util.List;

import payments.stone.com.br.desafiomobile.Product;

/**
 * Created by william.gouvea on 9/21/17.
 */

public interface HomeView {
    void showProducts(List<Product> productList);

    public void showLoading();

    public void hideLoading();

    public void showError(String error);

    public void hideError();
}
