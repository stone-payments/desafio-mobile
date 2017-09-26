package payments.stone.com.br.desafiomobile.commons;

import payments.stone.com.br.desafiomobile.checkout.AddCartItemDialog;
import payments.stone.com.br.desafiomobile.model.CartItem;
import payments.stone.com.br.desafiomobile.model.Product;

/**
 * Created by william.gouvea on 9/20/17.
 */

public interface Navigation {

    void whenGoToDetails(Product product);

    void whenGoToHome();

    void showQuantityDialog(CartItem current, AddCartItemDialog.AddCartItemDialogListener listener);
}
