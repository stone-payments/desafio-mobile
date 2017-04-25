package com.example.leonardo.desafiomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonardo.desafiomobile.auxiliares.CustomListAdapter2;
import com.example.leonardo.desafiomobile.auxiliares.Produto;

import java.util.ArrayList;

public class Carrinho extends AppCompatActivity {

    ArrayList<Produto> carrinho;
    RecyclerView rv;
    Button b1,b2;
    TextView tv1;
    String nome, preço, data, imagem, zipcode, vendedor, qtd;
    CustomListAdapter2 adapter;
    private boolean resumeHasRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        rv = (RecyclerView) findViewById(R.id.rv3);
        b1 = (Button) findViewById(R.id.button5);
        b2 = (Button) findViewById(R.id.button4);
        tv1 = (TextView) findViewById(R.id.totalC);
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
        adapter = new CustomListAdapter2(getApplicationContext(),carrinho);
        rv.setAdapter(adapter);
        atualizar();


    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!resumeHasRun){
            resumeHasRun = true;
            return;
        }
            Intent i2 = this.getIntent();
            Bundle extras2 = i2.getExtras();
            this.nome = extras2.getString("NOME_KEY");
            this.preço = extras2.getString("PREÇO_KEY");
            this.data = extras2.getString("DATE_KEY");
            this.imagem = extras2.getString("IMAGEM_KEY");
            this.zipcode = extras2.getString("ZIPCODE_KEY");
            this.vendedor = extras2.getString("VENDEDOR_KEY");
            this.qtd = extras2.getString("QTD_KEY");
            int i;
            for (i = 0 ;i<carrinho.size();i++) {
                Produto obj = carrinho.get(i);
                if (this.nome.equals(obj.getTitle())) {
                    carrinho.remove(i);
                    addC();
                    adapter.notifyDataSetChanged();
                    atualizar();
                    i=carrinho.size();
                }
            }
            if(i==carrinho.size()) {
                addC();
                adapter.notifyDataSetChanged();
                atualizar();
            }
        }

    @Override
    protected void onNewIntent(Intent intent){
        if (intent != null){
            setIntent(intent);
        }
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

    public void abrirLista(View view){
        Intent i1 = new Intent(getApplicationContext(),Lista.class);
        i1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i1);
    }

    public void atualizar(){
        double a =0;
        Produto obj,copy;
        for(int i=0; i<carrinho.size();i++){
            obj = carrinho.get(i);
            copy = obj;
            copy.setPrice(copy.getPrice1().replace("R$",""));
            a = a + Double.parseDouble(copy.getPrice1())*Double.parseDouble(copy.getQuantidade());
        }
        String texto = Double.toString(a);
        tv1.setText("R$ " + texto);
    }
    public void confirmarC(View view){
        Intent i12 = new Intent(getApplicationContext(), Transacao.class);
        i12.putExtra("TOTAL_KEY",tv1.getText());
        startActivity(i12);
    }
}
