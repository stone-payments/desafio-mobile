package payments.stone.com.br.desafiomobile.cart;

import java.util.List;

/**
 * Created by william.gouvea on 9/22/17.
 */

interface CartView {
    void showTotalPrice(List<CartItem> cartItems);

    void showCartItems(List<CartItem> items);

    public void showLoading();

    public void hideLoading();

    public void showError(String error);

    public void hideError();
}
