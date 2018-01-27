package br.com.valdir.desafiolojastarwars;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.valdir.desafiolojastarwars.sync.ProdutosSyncAdapter;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {

    public static final String PRODUTO_DETALHE_URI = "PRODUTO";

    static Carrinho mCarrinho;

    private boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment_file_detalhe) != null) {

            if(savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_file_detalhe, new ProdutoDetalheFragment())
                        .commit();
            }

            isTablet = true;
        } else {
            isTablet = false;
        }

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        mainFragment.setUseProdutoDestaque(!isTablet);

        ProdutosSyncAdapter.initializeSyncAdapter(this);

    }

    @Override
    public void onItemSelected(Uri uri) {
        if (isTablet) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ProdutoDetalheFragment detalheFragment = new ProdutoDetalheFragment();
            Bundle bundle = new Bundle();

            bundle.putParcelable(MainActivity.PRODUTO_DETALHE_URI, uri);
            detalheFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fragment_file_detalhe, detalheFragment);
            fragmentTransaction.commit();
        } else {

            Intent intent = new Intent(this, ProdutoDetalheActivity.class);
            intent.setData(uri);
            startActivity(intent);
        }
    }
}
