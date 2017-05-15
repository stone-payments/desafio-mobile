package com.jademcosta.starstore.creditCard;


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
}
