package com.example.leonardo.desafiomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.leonardo.desafiomobile.auxiliares.CustomListAdapter2;
import com.example.leonardo.desafiomobile.auxiliares.Produto;

import java.util.ArrayList;

public class Carrinho extends AppCompatActivity {

    ArrayList<Produto> carrinho;
    RecyclerView rv;
    Button b1,b2;
    String nome, preço, data, imagem, zipcode, vendedor, qtd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        rv = (RecyclerView) findViewById(R.id.rv3);
        b1 = (Button) findViewById(R.id.button5);
        b2 = (Button) findViewById(R.id.button4);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(linearLayoutManager);


        Intent i1 = this.getIntent();
        carrinho = new ArrayList<>();
        Bundle extras = i1.getExtras();

        this.nome = extras.getString("NOME_KEY");
        this.preço = extras.getString("PREÇO_KEY");
        this.data = extras.getString("DATE_KEY");
        this.imagem = extras.getString("IMAGEM_KEY");
        this.zipcode = extras.getString("ZIPCODE_KEY");
        this.vendedor = extras.getString("VENDEDOR_KEY");
        this.qtd = extras.getString("QTD_KEY");
        addC();
        CustomListAdapter2 adapter = new CustomListAdapter2(getApplicationContext(),carrinho);
        rv.setAdapter(adapter);


    }
    public void addC(){
        this.carrinho.add(new Produto(
                nome,
                preço,
                zipcode,
                vendedor,
                imagem,
                zipcode,
                qtd));
    }
}
