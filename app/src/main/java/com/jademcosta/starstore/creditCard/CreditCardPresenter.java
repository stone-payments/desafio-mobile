package com.jademcosta.starstore.creditCard;


import android.content.Context;
import android.content.Intent;

import com.jademcosta.starstore.entity.CreditCard;
import com.jademcosta.starstore.entity.Payment;
import com.jademcosta.starstore.transactionsList.TransactionsListActivity;

public class CreditCardPresenter implements CreditCardContract.View.Presenter,
        CreditCardContract.Model.Presenter {

    private CreditCardContract.View view;

    public void setView(CreditCardContract.View view) {
        this.view = view;
    }

    private CreditCardContract.Model model;

    public void setModel(CreditCardContract.Model model) {
        this.model = model;
    }

    @Override
    public void sendButtonClicked() {
        view.showLoading();

        CreditCard creditCard = new CreditCard(view.getCreditCardOwnerName(),
                view.getCreditCardCvv(), view.getCreditCardExpirationDate(),
                view.getCreditCardNumber());

        model.payWithCreditCard(creditCard);
    }

    @Override
    public void okButtonClicked(Context context) {
        Intent intent = TransactionsListActivity.newIntent(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void paymentSuccessful(Payment payment) {
        model.emptyCart();
        model.saveTransaction(payment);
        view.hideLoading();
        view.showSuccessfulPayment();
    }

    @Override
    public void paymentFailed() {
        view.hideLoading();
        view.showError();
    }
}
