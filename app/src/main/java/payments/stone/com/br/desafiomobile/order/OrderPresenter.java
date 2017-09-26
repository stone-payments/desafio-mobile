package payments.stone.com.br.desafiomobile.order;

import android.os.Handler;

import java.util.List;

import payments.stone.com.br.desafiomobile.ShopitApplication;
import payments.stone.com.br.desafiomobile.data.ProductRepositoryImpl;
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
                ProductRepositoryImpl productRepository = ShopitApplication.getInstance().provideRepository();
                List<Order> orders = productRepository.findAllOrders(false);
                mView.hideLoading();
                mView.showOrders(orders);
            }
        },2000);

        return this;
    }
}
