package payments.stone.com.br.desafiomobile.order;

import java.util.List;

import payments.stone.com.br.desafiomobile.model.Order;
import payments.stone.com.br.desafiomobile.views.BaseView;

/**
 * Created by william.gouvea on 9/25/17.
 */

public interface OrderView extends BaseView{

    void showOrders(List<Order> orders);
}
