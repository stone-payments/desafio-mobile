package com.jademcosta.starstore.creditCard;


import android.support.v7.app.AppCompatActivity;

public class CreditCardActivity extends AppCompatActivity implements CreditCardContract.View {

    private Presenter presenter;

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
