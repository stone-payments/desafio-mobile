package mobile.desafio.stone.desafiomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import BD.BD;
import BD.Transaction;

/**
 * Created by Gerson on 07/04/2017.
 */

public class ConfirmTransaction extends AppCompatActivity {

    private int price = 0;
    EditText cardNumber;
    EditText cardOwner;
    EditText cardCode;
    EditText cardExpDate;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishbuy);

        this.cardNumber = (EditText) findViewById(R.id.edt_cardNumber);
        this.cardOwner = (EditText)findViewById(R.id.edt_cardOwner);
        this.cardCode = (EditText)findViewById(R.id.edt_secCode);
        this.cardExpDate = (EditText)findViewById(R.id.edt_expDate);

        Intent i = getIntent();
        this.price = i.getIntExtra("price", 0);
        TextView textView = (TextView) findViewById(R.id.value);
        textView.setText("R$ " + this.price);
    }

    public void finishPurchase(View v){
        if(isFieldsCorrect()){
        BD bd = new BD(this);
            String number = this.cardNumber.getText().toString();
            String lastFourDigits = number.substring(number.length()-4, number.length());
            bd.insert(new Transaction(this.price, cardOwner.getText().toString(), lastFourDigits));

            Toast.makeText(this, "Compra realizada com sucesso!", Toast.LENGTH_SHORT).show();
            Cart.getInstance().clean();
            finish();
        }
        else{
            Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean isFieldsCorrect(){
        if(cardNumber.getText().length() != 16 || cardOwner.getText().length() == 0 || cardExpDate.getText().length() != 5
                || cardCode.getText().length() != 3){
            return false;
        }

        return true;
    }
}
