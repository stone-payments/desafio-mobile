package payments.stone.com.br.desafiomobile.home;

import java.util.List;

import payments.stone.com.br.desafiomobile.model.Product;
import payments.stone.com.br.desafiomobile.views.BaseView;

/**
 * Created by william.gouvea on 9/21/17.
 */

public interface HomeView extends BaseView {
    void showProducts(List<Product> productList);
}
