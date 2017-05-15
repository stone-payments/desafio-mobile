package com.jademcosta.starstore.creditCard;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jademcosta.starstore.R;

public class CreditCardActivity extends AppCompatActivity implements CreditCardContract.View {

    private Presenter presenter;

    private Button sendButton;
    private EditText ownerName;
    private EditText expirationDate;
    private EditText cvv;
    private EditText creditCardNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        initializeViews();
        initializeListeners();

        CreditCardInjector injector = new CreditCardInjector();
        injector.inject(this);

//        presenter.onCreate();
    }

    private void initializeListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: jade: do something
            }
        });
    }

    private void initializeViews() {
        sendButton = (Button) findViewById(R.id.activity_credit_card_button_send);
        ownerName = (EditText) findViewById(R.id.activity_credit_card_input_name);
        expirationDate = (EditText) findViewById(R.id.activity_credit_card_input_expiration_date);
        cvv = (EditText) findViewById(R.id.activity_credit_card_input_cvv);
        creditCardNumber = (EditText) findViewById(R.id.activity_credit_card_input_credit_card_number);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
