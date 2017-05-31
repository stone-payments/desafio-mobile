package com.hernand.starwars.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ListView;
import android.widget.Toast;

import com.hernand.starwars.R;
import com.hernand.starwars.adapters.TransactionAdapter;
import com.hernand.starwars.model.Transact;

import java.io.Serializable;
import java.util.List;

public class TransactionsActivity extends BaseCompatActivity {

    private List<Transact> transacaoLista;

    private ListView lvItemsTransacoes;

    public static void navigate(Activity activity, List<Transact> transacaoLista){
        Intent intent = new Intent(activity, TransactionsActivity.class);
        intent.putExtra(EXTRA_TRANSACOES, (Serializable) transacaoLista);
        ActivityCompat.startActivity(activity,intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_transactions);

        initComponents();

        transacaoLista = (List<Transact>) getIntent().getSerializableExtra(EXTRA_TRANSACOES);
        if(transacaoLista != null && !transacaoLista.isEmpty()) {
            lvItemsTransacoes.setAdapter(new TransactionAdapter(this, transacaoLista));
        }
        else {
            Toast.makeText(this, getString(R.string.sem_transacoes_carrinho), Toast.LENGTH_LONG).show();
        }
    }

    private void initComponents() {
        initToolbar(getText(R.string.transacoes).toString());
        lvItemsTransacoes = (ListView) findViewById(R.id.lvItemsTransacoes);
    }
}
