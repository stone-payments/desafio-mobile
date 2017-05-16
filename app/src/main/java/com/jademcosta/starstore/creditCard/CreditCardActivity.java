package com.jademcosta.starstore.creditCard;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    private ProgressDialog dialog;

    public static Intent newIntent(Context context) {
        return new Intent(context.getApplicationContext(), CreditCardActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        initializeViews();
        initializeListeners();

        CreditCardInjector injector = new CreditCardInjector();
        injector.inject(this, getApplicationContext());
    }

    private void initializeListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendButtonClicked();
            }
        });
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.credit_card_screen_title));
        setSupportActionBar(toolbar);

        sendButton = (Button) findViewById(R.id.activity_credit_card_button_send);
        ownerName = (EditText) findViewById(R.id.activity_credit_card_input_name);
        expirationDate = (EditText) findViewById(R.id.activity_credit_card_input_expiration_date);
        cvv = (EditText) findViewById(R.id.activity_credit_card_input_cvv);
        creditCardNumber = (EditText) findViewById(R.id.activity_credit_card_input_credit_card_number);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getCreditCardNumber() {
        return creditCardNumber.getText().toString();
    }

    @Override
    public String getCreditCardOwnerName() {
        return ownerName.getText().toString();
    }

    @Override
    public String getCreditCardExpirationDate() {
        return expirationDate.getText().toString();
    }

    @Override
    public String getCreditCardCvv() {
        return cvv.getText().toString();
    }

    @Override
    public void showLoading() {
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
    }

    @Override
    public void showSuccessfulPayment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Pagamento aceito")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.okButtonClicked(CreditCardActivity.this);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void showError() {
        Snackbar.make(sendButton, getString(R.string.credit_card_payment_error), Snackbar.LENGTH_LONG).show();
    }
}
