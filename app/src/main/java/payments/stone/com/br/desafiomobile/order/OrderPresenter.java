package payments.stone.com.br.desafiomobile.order;

import android.os.Handler;

import java.util.List;

import payments.stone.com.br.desafiomobile.ShopitApplication;
import payments.stone.com.br.desafiomobile.data.ShopRepository;
import payments.stone.com.br.desafiomobile.model.Order;

/**
 * Created by william.gouvea on 9/25/17.
 */

public class OrderPresenter {
    private OrderView mView;

    public OrderPresenter(OrderView mView) {
        this.mView = mView;
    }

    public OrderPresenter loadOrders(){
        mView.showLoading();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ShopRepository shopRepository = ShopitApplication.getInstance().provideRepository();
                List<Order> orders = shopRepository.findAllOrders(false);
                mView.hideLoading();
                mView.showOrders(orders);
            }
        },2000);

        return this;
    }
}
