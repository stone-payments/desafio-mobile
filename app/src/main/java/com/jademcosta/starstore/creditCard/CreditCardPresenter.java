package com.jademcosta.starstore.creditCard;


import com.jademcosta.starstore.entity.CreditCard;
import com.jademcosta.starstore.entity.Payment;

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
    public void paymentSuccessful(Payment payment) {
        view.hideLoading();
        //TODO: jade: save transaction, show success modal
    }

    @Override
    public void paymentFailed() {
        view.hideLoading();
        view.showError();
    }
}
