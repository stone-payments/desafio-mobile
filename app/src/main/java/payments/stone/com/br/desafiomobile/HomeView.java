package payments.stone.com.br.desafiomobile;

import java.util.List;

/**
 * Created by william.gouvea on 9/21/17.
 */

interface HomeView {
    void showProducts (List<Product> productList);

    void showLoading ();

    void hideLoading ();

    void showError (String error);

    void hideError ();
}
