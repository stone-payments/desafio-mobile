package com.jademcosta.starstore.creditCard;


import com.jademcosta.starstore.database.ItemsRepository;
import com.jademcosta.starstore.entity.CreditCard;
import com.jademcosta.starstore.entity.Item;
import com.jademcosta.starstore.entity.Payment;
import com.jademcosta.starstore.network.CreditCardApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditCardModel implements CreditCardContract.Model {

    private Presenter presenter;
    private CreditCardApi api;
    private ItemsRepository itemsRepository;

    public CreditCardModel(CreditCardApi api, ItemsRepository itemsRepository) {
        this.api = api;
        this.itemsRepository = itemsRepository;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void payWithCreditCard(final CreditCard creditCard) {
        final Payment payment = new Payment(creditCard, getCartValue());

        api.postPayment(payment, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    presenter.paymentSuccessful(payment);
                } else {
                    presenter.paymentFailed();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                presenter.paymentFailed();
            }
        });
    }

    @Override
    public void emptyCart() {
        itemsRepository.clearAll();
    }

    private String getCartValue() {
        int total = 0;
        for(Item item : itemsRepository.getItems()) {
            total += item.getPrice();
        }
        return String.valueOf(total);
    }
}
