package payments.stone.com.br.desafiomobile.checkout;

import java.util.List;

import payments.stone.com.br.desafiomobile.views.BaseView;
import payments.stone.com.br.desafiomobile.model.CartItem;

/**
 * Created by william.gouvea on 9/22/17.
 */

public interface CartView extends BaseView {
    void showTotalPrice(String totalPrice);

    void showCartItems(List<CartItem> items);

}
