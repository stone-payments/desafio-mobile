package com.example.leonardo.desafiomobile.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            t1 = new Transação();
            t1.setnCartão(et1.getText().toString());
            t1.setCvv(Integer.parseInt(et2.getText().toString()));
            t1.setNome_Titular(et3.getText().toString());
            t1.setVencimento(et4.getText().toString());
            t1.setValor(Double.parseDouble(tv1.getText().toString().replace("R$ ",""))*100);
        }


        @Override
        protected String doInBackground(String... urls) {
            return Post(urls[0],t1);
        }

        @Override
        protected void onPostExecute(String result){
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            if(result.substring(0,3).equals("201")){
                newDB();
                Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i1);
                finish();
            }
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
        db.setUltimosDig(Integer.parseInt(et1.getText().toString().substring(12,16)));
        db1 = new DbHandler(this,"MeuCU.db",null,1);
        db1.addData(db);
    }


}