package br.com.valdir.desafiolojastarwars;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;

/**
 * Created by valdy on 28/01/2018.
 */

public class CartaoCreditoActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartao_credito_form);

        CardForm cardForm = findViewById(R.id.credito_form);
        final TextView txtValorPagamento = findViewById(R.id.payment_amount);
        Button btnPagamento = findViewById(R.id.btn_pay);

        txtValorPagamento.setText("0");

        if(getIntent().hasExtra("VALOR_PAGAMENTO")){
            Bundle extras = getIntent().getExtras();
            txtValorPagamento.setText(extras.getString("VALOR_PAGAMENTO"));
            Log.d("CC", extras.getString("VALOR_PAGAMENTO"));
        }

        btnPagamento.setText(String.format("Pagar %s", txtValorPagamento.getText()));

        // Modelo do post
        //{
        //    "card_number":"1234123412341234",
        //        "value":7990,
        //        "cvv":789,
        //        "card_holder_name":"Luke Skywalker",
        //        "exp_date":"12/24"
        //}

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Log.d("1a:", "card_number:"+card.getNumber());
                Log.d("2a", "value:"+txtValorPagamento.getText().toString());
                Log.d("3a", "cvv:"+card.getCVC());
                Log.d("4a", "card_holder_name:"+card.getName());
                Log.d("5a", String.format("exp_date: %d/%d", card.getExpMonth(), card.getExpYear()));
            }
        });

    }
}
