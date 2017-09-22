package payments.stone.com.br.desafiomobile.details;

import payments.stone.com.br.desafiomobile.BaseView;
import payments.stone.com.br.desafiomobile.Product;

/**
 * Created by william.gouvea on 9/22/17.
 */

public interface DetailsView extends BaseView {
    void showDetails(Product product);
}
