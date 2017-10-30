package com.example.victo.desafiostone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {


    ImageView btItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        btItens  = (ImageView) findViewById(R.id.btItens);


    }

    //Ir para tela iten
    public void telaItem (View V){
        Intent intent = new Intent(MainActivity.this, ItensActivity.class);
        startActivity(intent);

    }


}


