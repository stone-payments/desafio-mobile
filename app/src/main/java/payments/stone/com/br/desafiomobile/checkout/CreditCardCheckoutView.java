package payments.stone.com.br.desafiomobile.checkout;

import payments.stone.com.br.desafiomobile.commons.Navigation;
import payments.stone.com.br.desafiomobile.model.Order;

/**
 * Created by william.gouvea on 9/22/17.
 */

public interface CreditCardCheckoutView extends CartView {
    Order filledOrder();

    Navigation navigation();
}
