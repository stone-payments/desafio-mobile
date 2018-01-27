package br.com.valdir.desafiolojastarwars;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ProdutoDetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);

        Intent intent = getIntent();
        Uri uri = intent.getData();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ProdutoDetalheFragment fragment = new ProdutoDetalheFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.PRODUTO_DETALHE_URI, uri);
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_file_detalhe, fragment);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detalhes do Produto");
//        actionBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
//        actionBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //What to do on back clicked
//            }
//        });
        // end toolbar

        fragmentTransaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
