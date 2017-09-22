package payments.stone.com.br.desafiomobile.cart;

import java.util.List;

import payments.stone.com.br.desafiomobile.BaseView;

/**
 * Created by william.gouvea on 9/22/17.
 */

interface CartView extends BaseView {
    void showTotalPrice(List<CartItem> cartItems);

    void showCartItems(List<CartItem> items);

}
