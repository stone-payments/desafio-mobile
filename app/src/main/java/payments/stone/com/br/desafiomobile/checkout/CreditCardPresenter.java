package payments.stone.com.br.desafiomobile.checkout;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import payments.stone.com.br.desafiomobile.ShopitApplication;
import payments.stone.com.br.desafiomobile.commons.ShopApi;
import payments.stone.com.br.desafiomobile.commons.Utils;
import payments.stone.com.br.desafiomobile.data.ProductRepositoryImpl;
import payments.stone.com.br.desafiomobile.model.Order;
import payments.stone.com.br.desafiomobile.views.BasePresenter;
import payments.stone.com.br.desafiomobile.model.CartItem;
import payments.stone.com.br.desafiomobile.model.Product;
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
        final Order order = mView.filledOrder();
        ShopApi shopApi = ShopitApplication.getInstance().provideApi();

        Call<Order> call =  shopApi.checkout(order);

        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                //Show SnackBar
                ProductRepositoryImpl productRepository = ShopitApplication.getInstance().provideRepository();
                Order processed = response.body();
                processed.transactionDate(Utils.dateToIso(new Date()));
                productRepository.save(processed);
                mView
                        .navigation()
                        .whenGoToHome();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                //Show SnackBar
                mView.showError("");
            }
        });

        return this;
    }

    public CreditCardPresenter loadCart(){
        mView.showLoading();

        mItems.add(
                new CartItem(
                        new Product()
                                .title("Yoda Poster")
                                .seller("Lucas Arts")
                                .price(90000)).increment(2));
        mItems.add(
                new CartItem(
                        new Product()
                                .title("Camisa StormTrooper")
                                .seller("Lucas Arts")
                                .thumb("http://mlb-s1-p.mlstatic.com/moletom-star-wars-stormtrooper-12754-MLB20066273702_032014-F.jpg")
                                .price(725000))
                        .increment(1));

        mView.hideLoading();
        mView.showTotalPrice(mItems);
        mView.showCartItems(mItems);

        return this;
    }
}
