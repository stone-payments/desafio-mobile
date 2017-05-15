package com.jademcosta.starstore.creditCard;


public class CreditCardModel implements CreditCardContract.Model {

    private Presenter presenter;

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
