package payments.stone.com.br.desafiomobile.cart;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

import payments.stone.com.br.desafiomobile.BasePresenter;
import payments.stone.com.br.desafiomobile.Product;

/**
 * Created by william.gouvea on 9/22/17.
 */

public class CartPresenter extends BasePresenter {
    private CartView mView;

    public CartPresenter(CartView mView) {
        this.mView = mView;
        create();
    }

    public CartPresenter loadCart() {
        mView.showLoading();
        final List<CartItem> items = new ArrayList<>();
        items.add(
                new CartItem(
                        new Product()
                                .title("Yoda Poster")
                                .seller("Lucas Arts")
                                .price(90000)).increment(2));

        items.add(
                new CartItem(
                        new Product()
                                .title("Camisa StormTrooper")
                                .seller("Lucas Arts")
                                .thumb("http://mlb-s1-p.mlstatic.com/moletom-star-wars-stormtrooper-12754-MLB20066273702_032014-F.jpg")
                                .price(725000))
                        .increment(1));

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.hideLoading();
                mView.showTotalPrice(items);
                mView.showCartItems(items);
            }
        }, 1200);

        return this;
    }

    @Override
    public void create() {

    }
}
