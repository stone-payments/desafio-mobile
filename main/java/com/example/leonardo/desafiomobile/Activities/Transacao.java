package com.example.leonardo.desafiomobile.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonardo.desafiomobile.Objetos.Database;
import com.example.leonardo.desafiomobile.Objetos.Transação;
import com.example.leonardo.desafiomobile.R;
import com.example.leonardo.desafiomobile.auxiliares.DbHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Transacao extends AppCompatActivity {

    EditText et1,et2,et3,et4;
    TextView tv1;
    Button b1,b2;
    Transação t1;
    Intent i;
    Database db;
    GregorianCalendar c;
    DbHandler db1;
    private volatile boolean running = true;
    String ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);

        tv1 = (TextView) findViewById(R.id.valorT);
        et2 = (EditText) findViewById(R.id.cvvT);
        et3 = (EditText) findViewById(R.id.nomeTitularT);
        et1 = (EditText) findViewById(R.id.nCartaoT);
        et4 = (EditText) findViewById(R.id.validadeT);
        b1 = (Button) findViewById(R.id.confirmarT);
        b2 = (Button) findViewById(R.id.retornoT);

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(getApplicationContext(), "Por Favor, escreva a data no formato mm/aa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        i = this.getIntent();
        tv1.setText(i.getExtras().getString("TOTAL_KEY"));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if(!validar())
                            Toast.makeText(getApplicationContext(),"Campos não preenchidos!",Toast.LENGTH_LONG).show();
                        else new sendJSON().execute("https://private-d9dbe-desafiomobile.apiary-mock.com/transacoes");
                }
            }
        );


    }

    class sendJSON extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            running = true;
            t1 = new Transação();
            t1.setnCartão(et1.getText().toString());
            t1.setCvv(Integer.parseInt(et2.getText().toString()));
            t1.setNome_Titular(et3.getText().toString());
            t1.setVencimento(et4.getText().toString());
            t1.setValor(Double.parseDouble(tv1.getText().toString().replace("R$ ",""))*100);
            if(et1.getText().length()<4){
                Toast.makeText(getApplicationContext(),"Número de Cartão Inválido!",Toast.LENGTH_SHORT).show();
                cancel(true);
            }
            if(et2.getText().length()!=3){
                Toast.makeText(getApplicationContext(),"Número de CVV Inválido!",Toast.LENGTH_LONG).show();
                cancel(true);
            }
            if(et4.getText().length()!=5 || et4.getText().toString().charAt(2) != '/'){
                Toast.makeText(getApplicationContext(),"Data de vencimento Inválida!",Toast.LENGTH_LONG).show();
                cancel(true);
            }
        }


        @Override
        protected String doInBackground(String... urls) {
            if(running){
            return Post(urls[0],t1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            if(running) {
                if (result.substring(0, 3).equals("201")) {
                    newDB();
                    Intent i1 = new Intent();
                    i1.putExtra("result",result);
                    setResult(Carrinho.RESULT_OK,i1);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Transação Falhou! Servidor Não responde", Toast.LENGTH_LONG ).show();
                }
            }
        }
        @Override
        protected void onCancelled() {
            running = false;
        }
    }

    public boolean validar() {
        return !et1.getText().toString().trim().equals("") &&
                !et2.getText().toString().trim().equals("") &&
                !et3.getText().toString().trim().equals("") &&
                !tv1.getText().toString().trim().equals("")&&
                !et4.getText().toString().trim().equals("");
    }

    public static String Post(String newurl, Transação t){
        String result ="";
        try{
            URL url = new URL(newurl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            String json;

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("card_number", t.getnCartão());
            jsonObject.accumulate("value", t.getValor());
            jsonObject.accumulate("cvv", t.getCvv());
            jsonObject.accumulate("card_holder_name", t.getNome_Titular());
            jsonObject.accumulate("exp_date", t.getVencimento());

            json = jsonObject.toString();

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(),"UTF-8");
            out.write(json);
            out.flush();
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK){
                result = Integer.toString(urlConnection.getResponseCode()) + ": " + urlConnection.getResponseMessage();
            }
            else{
                result = Integer.toString(urlConnection.getResponseCode()) + ": " + urlConnection.getResponseMessage();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void newDB(){
        db = new Database();
        c = new GregorianCalendar();
        db.setValor(t1.getValor());
        db.setDeh(c.getTime().toString());
        db.setNome(et3.getText().toString());
        int i = et1.getText().length();
        db.setUltimosDig(Integer.parseInt(et1.getText().toString().substring(i-4,i)));
        db1 = new DbHandler(this,"Finalmente.db",null,1);
        db1.addData(db);
    }

    public void retornar(View view){
        Intent returnIntent = new Intent();
        setResult(Carrinho.RESULT_CANCELED, returnIntent);
        finish();
    }


}