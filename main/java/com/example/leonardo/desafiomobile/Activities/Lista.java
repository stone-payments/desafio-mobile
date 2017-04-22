package com.example.leonardo.desafiomobile.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.leonardo.desafiomobile.R;
import com.example.leonardo.desafiomobile.auxiliares.ClickListener;
import com.example.leonardo.desafiomobile.auxiliares.CustomListAdapter;
import com.example.leonardo.desafiomobile.Objetos.Produto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class Lista extends AppCompatActivity{

    private static String url = "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json";

    ArrayList<Produto> listaProdutos;
    RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        listaProdutos = new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.list);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new readJSON().execute(url);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        rv.setLayoutManager(gridLayoutManager);
        rv.addOnItemTouchListener(
                new ClickListener(getApplicationContext(), rv, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Produto clickedObj = listaProdutos.get(position);
                        Intent i = new Intent(getApplicationContext(), Conf.class);
                        i.putExtra("NOME_KEY", clickedObj.getTitle());
                        i.putExtra("PREÇO_KEY", clickedObj.getPrice());
                        i.putExtra("VENDEDOR_KEY", clickedObj.getSeller());
                        i.putExtra("IMAGEM_KEY", clickedObj.getThumbnailHd());
                        i.putExtra("ZIPCODE_KEY", clickedObj.getZipcode());
                        i.putExtra("DATE_KEY", clickedObj.getDate());
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    class readJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            String a = "0";
            try {
                JSONArray jsonArray = new JSONArray(content);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject produto = jsonArray.getJSONObject(i);
                    listaProdutos.add(new Produto(
                            produto.getString("title"),
                            produto.getString("price"),
                            produto.getString("zipcode"),
                            produto.getString("seller"),
                            produto.getString("thumbnailHd"),
                            produto.getString("date"),
                            a
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), listaProdutos);
            rv.setAdapter(adapter);
        }
    }

    private static String readURL(String theUrl){
        StringBuilder content = new StringBuilder();
        try{
            URL url = new URL(theUrl);
            URLConnection urlConnecticon = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnecticon.getInputStream()));
            String line;
            while((line = bufferedReader.readLine())!= null){
                content.append(line+"\n");
            }
            bufferedReader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return content.toString();
    }

    public void retornar(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }
}


