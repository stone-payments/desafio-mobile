package com.jademcosta.starstore.creditCard;


import com.jademcosta.starstore.database.ItemsRepository;
import com.jademcosta.starstore.database.TransactionsRepository;
import com.jademcosta.starstore.entity.CreditCard;
import com.jademcosta.starstore.entity.Item;
import com.jademcosta.starstore.entity.Payment;
import com.jademcosta.starstore.entity.Transaction;
import com.jademcosta.starstore.network.CreditCardApi;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditCardModel implements CreditCardContract.Model {

    private static final String DATE_DIVIDER = "/";
    private static final String HOUR_MINUTE_DIVIDER = ":";

    private Presenter presenter;
    private CreditCardApi api;
    private ItemsRepository itemsRepository;
    private TransactionsRepository transactionsRepository;

    public CreditCardModel(CreditCardApi api, ItemsRepository itemsRepository,
                           TransactionsRepository transactionsRepository) {
        this.api = api;
        this.itemsRepository = itemsRepository;
        this.transactionsRepository = transactionsRepository;
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

    @Override
    public void saveTransaction(Payment payment) {
        Transaction transaction = buildTransactionFromPayment(payment);
        transactionsRepository.add(transaction);
    }

    private String getCartValue() {
        int total = 0;
        for(Item item : itemsRepository.getItems()) {
            total += item.getPrice();
        }
        return String.valueOf(total);
    }

    private Transaction buildTransactionFromPayment(Payment payment) {
        return new Transaction(payment.getOwnerName(),
                payment.getLastDigitsFromCardNumber(Transaction.NUMBER_OF_CARD_DIGITS),
                payment.getValue(), getCurrentDate());
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();

        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + DATE_DIVIDER +
                String.valueOf(calendar.get(Calendar.MONTH)) + DATE_DIVIDER +
                String.valueOf(calendar.get(Calendar.YEAR)) + " " +
                String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + HOUR_MINUTE_DIVIDER +
                String.valueOf(calendar.get(Calendar.MINUTE));
    }
}
