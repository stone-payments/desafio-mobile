package payments.stone.com.br.desafiomobile.checkout;


import android.os.Handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import payments.stone.com.br.desafiomobile.ShopitApplication;
import payments.stone.com.br.desafiomobile.data.ShopApi;
import payments.stone.com.br.desafiomobile.commons.Utils;
import payments.stone.com.br.desafiomobile.model.Cart;
import payments.stone.com.br.desafiomobile.model.CartItem;
import payments.stone.com.br.desafiomobile.model.Order;
import payments.stone.com.br.desafiomobile.views.BasePresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by william.gouvea on 9/22/17.
 */

public class CreditCardPresenter extends BasePresenter {

    private CreditCardView mView;

    private List<CartItem> mItems;

    public CreditCardPresenter(CreditCardView mView) {
        this.mView = mView;
        create();
    }

    @Override
    public void create() {
        mItems = (List<CartItem>) mView.handleIntent();

        if(mItems == null) {
            mItems = new ArrayList<>();
        }
    }

    public CreditCardPresenter checkout(){
        mView.showLoading();
        final Order order = mView.filledOrder();
        ShopApi shopApi = ShopitApplication.getInstance().provideApi();
        Call<Order> call =  shopApi.checkout(order);

        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                //Show SnackBar
                Order processed = response.body();

                Date now = new Date();

                processed.cardHolder(order.getCardHolder());
                processed.transactionDate(Utils.dateToIso(now));
                processed.transactionDateObj(now);
                processed.value(order.getValue());

                ShopitApplication
                        .getInstance()
                        .provideRepository()
                        .save(processed);

                ShopitApplication
                        .getInstance()
                        .provideCart()
                        .reset();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.hideLoading();

                        mView
                                .navigation()
                                .whenGoToHome();
                    }
                },1000);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                //Show SnackBar
                mView.hideLoading();
                mView.showError("");
            }
        });

        return this;
    }

    public CreditCardPresenter loadCart(){
//        mView.showLoading();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Cart cart = ShopitApplication.getInstance().provideCart();
                mView.hideLoading();
                mView.showTotalPrice(cart.total());
                mView.showCartItems(cart.getItems());
            }
        }, 500);

        return this;
    }
}
