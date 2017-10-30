package com.example.victo.desafiostone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class CarrinhoActivity extends AppCompatActivity {

    private DBHelper dh;

    public static final String EXTRA_REPLY = "com.example.victo.desafiostone.extra.REPLY";

     ImageView imagem;
     TextView title,seller,price;
     EditText etNome, etNumCartao1, etNumCartao2, etNumCartao3, etNumCartao4, etMes , etAno, etCvv, etPrice;
     Button btComprar, btListar;
     Produto item = new Produto();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        this.dh = new DBHelper(this);

        etNome = (EditText) findViewById(R.id.etNome);
        etNumCartao1 = (EditText) findViewById(R.id.etNumCartao1);
        etNumCartao2 = (EditText) findViewById(R.id.etNumCartao2);
        etNumCartao3 = (EditText) findViewById(R.id.etNumCartao3);
        etNumCartao4 = (EditText) findViewById(R.id.etNumCartao4);
        etMes = (EditText) findViewById(R.id.etMes);
        etAno = (EditText) findViewById(R.id.etAno);
        etCvv = (EditText) findViewById(R.id.etCvv);

        btComprar = (Button) findViewById(R.id.btComprar);
        btListar = (Button) findViewById(R.id.btListar);

        imagem = (ImageView) findViewById(R.id.imageCarrinho);
        title = (TextView) findViewById(R.id.titleCarrinho);
        seller = (TextView) findViewById(R.id.SellerCarrinho);
        price = (TextView) findViewById(R.id.etPrice);


        Intent it = getIntent();
        Produto produto = (Produto) it.getSerializableExtra("produto");
        item = produto;
        Picasso.with(getApplicationContext()).load(produto.getImage()).into(imagem);
        title.setText(produto.getTitle());
        seller.setText(produto.getSeller());
        price.setText(produto.getPrice());


        btComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(etNome.getText().length()>0 && price.getText().length()>0 && etNumCartao4.getText().length()>0){
                    dh.insert(etNome.getText().toString(), price.getText().toString(), etNumCartao4.getText().toString());
                    AlertDialog.Builder adb = new AlertDialog.Builder(CarrinhoActivity.this);
                    adb.setTitle("Sucesso");
                    adb.setMessage("Comprado com Sucesso!");
                    adb.show();

                    etNome.setText(" ");
                    etNumCartao1.setText(" ");
                    etNumCartao2.setText(" ");
                    etNumCartao3.setText(" ");
                    etNumCartao4.setText(" ");
                    etCvv.setText(" ");
                    etMes.setText(" ");
                    etAno.setText(" ");
                }
                else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(CarrinhoActivity.this);
                    adb.setTitle("Erro");
                    adb.setMessage("Todo os campos devem ser preenchidos!");
                    adb.show();
                }
            }
        });

        btListar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                List<ItensBanco> produtos = dh.queryGetAll();
                if(produtos == null){
                    AlertDialog.Builder adb = new AlertDialog.Builder(CarrinhoActivity.this);
                    adb.setTitle("Mensagem");
                    adb.setMessage("Não há registros Salvos!");
                    adb.show();
                    return;
                }
                for(int i=0; i < produtos.size(); i++){
                    ItensBanco produto = (ItensBanco) produtos.get(i);

                    AlertDialog.Builder adb = new AlertDialog.Builder(CarrinhoActivity.this);
                    adb.setTitle("Registro " + i);
                    adb.setMessage("Preço: " + produto.getNome() + "\nCartão do Comprador:  **** " + produto.getCartao()
                                    + "\nNome do Comprador: " + produto.getPrice());
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adb.show();
                }
            }
        });

    }
}

