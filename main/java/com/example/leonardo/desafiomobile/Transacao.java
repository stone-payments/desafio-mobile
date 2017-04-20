package com.example.leonardo.desafiomobile;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Transacao extends AppCompatActivity {

    EditText et1,et2,et3,et4;
    TextView tv1;
    Button b1,b2;
    Transação t1;
    Intent i;

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
                        else new sendJSON().execute("http://polls.apiblueprint.org/transacoes");
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
            return Post(urls[0],t1,getApplicationContext());
        }
    }

    public boolean validar() {
        return !et1.getText().toString().trim().equals("") &&
                !et2.getText().toString().trim().equals("") &&
                !et3.getText().toString().trim().equals("") &&
                !tv1.getText().toString().trim().equals("")&&
                !et4.getText().toString().trim().equals("");
    }

    public static String Post(String newurl, Transação t, Context ctx){
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

            StringBuilder sb = new StringBuilder();
            int HttpResult = urlConnection.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while((line = br.readLine()) != null){
                    sb.append(line).append("\n");
                }
                result = sb.toString();
                br.close();
            }
            //else{
            //    Toast.makeText(ctx,urlConnection.getResponseMessage(),Toast.LENGTH_LONG).show();
            //}
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


}