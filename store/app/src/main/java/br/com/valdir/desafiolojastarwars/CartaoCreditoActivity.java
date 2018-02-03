package br.com.valdir.desafiolojastarwars;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import br.com.valdir.desafiolojastarwars.api.APIServicePost;
import br.com.valdir.desafiolojastarwars.api.ApiUtils;
import br.com.valdir.desafiolojastarwars.api.Infor;
import br.com.valdir.desafiolojastarwars.data.TransacoesContract;
import br.com.valdir.desafiolojastarwars.data.TransacoesDBHelper;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by valdy on 28/01/2018.
 */

public class CartaoCreditoActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private TransacoesDBHelper bancoTransacoes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartao_credito_form);

        bancoTransacoes = new TransacoesDBHelper(this);

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

                // -- Insere transacao no banco
                ContentValues transacao;
                long resultado;

                db = bancoTransacoes.getWritableDatabase();
                // data corrente calculo
                Calendar c = Calendar.getInstance();
                int ano = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH) + 1;
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int minuto = c.get(Calendar.MINUTE);

                String stDia, stMes, stHora, stMinuto;
                stDia = String.valueOf(dia);
                stMes = String.valueOf(mes);
                stHora = String.valueOf(hora);
                stMinuto = String.valueOf(minuto);
                // fim data corrente calculo

                transacao = new ContentValues();
                transacao.put(TransacoesContract.TransacaoEntry.COLUMN_USUARIO_ID, "1");
                transacao.put(TransacoesContract.TransacaoEntry.COLUMN_VALOR, Double.valueOf(txtValorPagamento.getText().toString()));
                transacao.put(TransacoesContract.TransacaoEntry.COLUMN_DATA, stDia + "/" + stMes + "/" + ano);
                transacao.put(TransacoesContract.TransacaoEntry.COLUMN_HORA, stHora + ":" + stMinuto);
                transacao.put(TransacoesContract.TransacaoEntry.COLUMN_ULT_4_DIGITOS_CARTAO, card.getLast4());
                transacao.put(TransacoesContract.TransacaoEntry.COLUMN_PORTADOR_CARTAO_NOME_COMPLETO, card.getName());

                resultado = db.insert(TransacoesContract.TransacaoEntry.TABLE_NAME, null, transacao);
                db.close();

                // -- Fim insere transacao no banco

                if (resultado == -1) {
                    Log.e("ErroBanco", "Incapaz de inserir dados no banco de transações");
                    Toast.makeText(getBaseContext(), "Não foi possível realizar o pagamento", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    pagamentoCartao(card.getNumber(), card.getName(),
                            String.format("exp_date: %d/%d", card.getExpMonth(), card.getExpYear()),
                            card.getCVC(), txtValorPagamento.getText().toString());
                }
            }
        });

    }

    private void pagamentoCartao(String cardNumber,
         String cardHolderName, String expDate, String cvv, String valor) {

        APIServicePost mService = ApiUtils.getAPIService();

        // prepare call in Retrofit 2.0
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("card_number", cardNumber);
            paramObject.put("card_holder_name", cardHolderName);
            paramObject.put("exp_date", expDate);
            paramObject.put("cvv", cvv);
            paramObject.put("value", valor);

            RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (paramObject).toString());

            Call<Infor> pago = mService.realizaPagamento(requestBody);

            pago.enqueue(new Callback<Infor>(){
                @Override
                public void onResponse(Call<Infor> call, Response<Infor> response) {

                    if (response.isSuccessful()) {
                        //showResponse(response.body().toString());
                        Log.i("CartaoCreditoActivity", "Pagamento enviado." + response.body().toString());
                        Toast.makeText(getBaseContext(), response.body().getResultado(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Infor> call, Throwable t) {
                    Log.e("CartaoCreditoActivity", "Incapaz de realizar o post para a API.");
                    Toast.makeText(getBaseContext(), "Não foi possível realizar o pagamento", Toast.LENGTH_LONG).show();
                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
