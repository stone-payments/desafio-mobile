package payments.stone.com.br.desafiomobile.details;

import payments.stone.com.br.desafiomobile.model.Product;
import payments.stone.com.br.desafiomobile.views.BaseView;

/**
 * Created by william.gouvea on 9/22/17.
 */

public interface DetailsView extends BaseView {
    void showDetails(Product product);
}
