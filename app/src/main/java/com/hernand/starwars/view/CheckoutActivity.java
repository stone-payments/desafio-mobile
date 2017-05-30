package com.hernand.starwars.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.hernand.starwars.R;
import com.hernand.starwars.adapters.ProductAdapter;
import com.hernand.starwars.vo.ProductVO;

import java.io.Serializable;
import java.util.List;

public class CheckoutActivity extends BaseCompatActivity {


    private List<ProductVO> listaProdutos;
    private FloatingActionButton fabFinalizar;
    private ListView lvItemsCarrinho;


    public static void navigate(Activity activity,List<ProductVO> produtoVOList){
        Intent intent = new Intent(activity, CheckoutActivity.class);
        intent.putExtra(EXTRA_PRODUTOS, (Serializable) produtoVOList);
        ActivityCompat.startActivity(activity,intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_checkout);

        initComponents();

        fabFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentActivity.navigate(CheckoutActivity.this,listaProdutos);
            }
        });

        listaProdutos = (List<ProductVO>) getIntent().getSerializableExtra(EXTRA_PRODUTOS);
        if(listaProdutos != null && !listaProdutos.isEmpty()) {
            fabFinalizar.setEnabled(true);
            lvItemsCarrinho.setAdapter(new ProductAdapter(this, listaProdutos));
        }
        else {
            fabFinalizar.setEnabled(false);
            Toast.makeText(this, getString(R.string.sem_produtos_carrinho), Toast.LENGTH_LONG).show();
        }
    }

    private void initComponents() {
        initToolbar(getText(R.string.seu_carrinho).toString());
        fabFinalizar = (FloatingActionButton) findViewById(R.id.fabFinalizar);
        lvItemsCarrinho = (ListView) findViewById(R.id.lvItemsCarrinho);
    }


}
