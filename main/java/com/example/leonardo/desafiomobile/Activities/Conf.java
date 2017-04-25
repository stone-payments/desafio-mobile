package com.example.leonardo.desafiomobile.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leonardo.desafiomobile.R;
import com.squareup.picasso.Picasso;

public class Conf extends AppCompatActivity {

    TextView tv2,tv4;
    ImageView iv1;
    EditText et1;
    Button b1;
    String nome, preço, vendedor, imagem, zipcode, data, quantidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        tv2 = (TextView) findViewById(R.id.tv2);
        tv4 = (TextView) findViewById(R.id.tv4);
        iv1 = (ImageView) findViewById(R.id.iv1);
        et1 = (EditText) findViewById(R.id.editText3);
        et1.setInputType(InputType.TYPE_CLASS_NUMBER);
        b1 = (Button) findViewById(R.id.button2);
        Intent i = this.getIntent();
        this.nome = i.getExtras().getString("NOME_KEY");
        this.preço = i.getExtras().getString("PREÇO_KEY");
        this.vendedor = i.getExtras().getString("VENDEDOR_KEY");
        this.imagem = i.getExtras().getString("IMAGEM_KEY");
        this.zipcode = i.getExtras().getString("ZIPCODE_KEY");
        this.data = i.getExtras().getString("DATA_KEY");
        tv2.setText(nome);
        tv4.setText("R$ " + preço);
        Picasso.with(this).load(imagem).into(iv1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),Carrinho.class);
                quantidade = et1.getText().toString();
                i1.putExtra("NOME_KEY",nome);
                i1.putExtra("PREÇO_KEY",preço);
                i1.putExtra("VENDEDOR_KEY",vendedor);
                i1.putExtra("IMAGEM_KEY",imagem);
                i1.putExtra("ZIPCODE_KEY",zipcode);
                i1.putExtra("DATE_KEY",data);
                i1.putExtra("QTD_KEY", quantidade);
                startActivity(i1);
                finish();
            }
        });
    }

    public void cancelar(View view){
        Intent i5 = new Intent(getApplicationContext(), Lista.class);
        i5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i5);
        finish();
    }
}
