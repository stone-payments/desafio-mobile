package br.com.valdir.desafiolojastarwars;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.valdir.desafiolojastarwars.data.ProdutosContract;
import br.com.valdir.desafiolojastarwars.sync.ProdutosSyncAdapter;

import static br.com.valdir.desafiolojastarwars.MainActivity.mCarrinho;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private int posicaoItem = ListView.INVALID_POSITION;

    private static final String KEY_POSICAO = "SELECIONADO";

    private ListView list;

    private ProdutosAdapter adapter;

    private boolean useProdutoDestaque = false;

    private static final int PRODUTOS_LOADER = 0;

    private ProgressDialog progressDialog;

    private TextView totalValorCarrinho;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        list = view.findViewById(R.id.list_produtos);

        adapter = new ProdutosAdapter(getContext(),null);
        adapter.setUseProdutoDestaque(useProdutoDestaque);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = ProdutosContract.ProdutoEntry.buildUriForProdutos(id);
                Callback callback = (Callback) getActivity();
                callback.onItemSelected(uri);
                posicaoItem = position;
            }
        });

        if(savedInstanceState != null && savedInstanceState.containsKey(KEY_POSICAO)) {
            posicaoItem = savedInstanceState.getInt(KEY_POSICAO);
        }

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.pd_carregando_titulo));
        progressDialog.setMessage(getString(R.string.pd_carregando_mensagem));
        progressDialog.setCancelable(false);

        getLoaderManager().initLoader(PRODUTOS_LOADER, null, this);


        Button btnCarrinho = view.findViewById(R.id.btn_ver_carrinho);
        btnCarrinho.setOnClickListener(onClickVerCarrinho());

        Button btnZerarCarrinho = view.findViewById(R.id.btn_zerar_carrinho);
        btnZerarCarrinho.setOnClickListener(onClickZerarCarrinho());

        Button btnPagar = view.findViewById(R.id.btn_pagar);
        btnPagar.setOnClickListener(onClickPagar());

        totalValorCarrinho = view.findViewById(R.id.txt_total_carrinho);
//        totalValorCarrinho.setText("Total do Carrinho = R$ " + String.format("%.2f", mCarrinho.getValue()));
        totalValorCarrinho.setText("Total do Carrinho = R$ 0,00");

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (posicaoItem != ListView.INVALID_POSITION) {
            outState.putInt(KEY_POSICAO, posicaoItem);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            list.smoothScrollToPosition(savedInstanceState.getInt(KEY_POSICAO));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_atualizar:
                ProdutosSyncAdapter.syncImmediately(getContext());
                Toast.makeText(getContext(), "Atualizando os produtos...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_config:
                startActivity(new Intent(getContext(), SettingsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getLoaderManager().restartLoader(PRODUTOS_LOADER, null, this);

        totalValorCarrinho.setText("Total do Carrinho = R$ " + String.format("%.2f", mCarrinho.getValue()));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        progressDialog.show();

        String[] projection = {
                ProdutosContract.ProdutoEntry._ID,
                ProdutosContract.ProdutoEntry.COLUMN_TITLE,
                ProdutosContract.ProdutoEntry.COLUMN_PRICE,
                ProdutosContract.ProdutoEntry.COLUMN_ZIPCODE,
                ProdutosContract.ProdutoEntry.COLUMN_SELLER,
                ProdutosContract.ProdutoEntry.COLUMN_THUMBNAILHD_PATH,
                ProdutosContract.ProdutoEntry.COLUMN_DATA
        };

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String ordem = preferences.getString(getString(R.string.prefs_ordem_key),"title");
        String popularValue = getResources().getStringArray(R.array.prefs_ordem_values)[0];

        String orderBy = null;
        if (ordem.equals(popularValue)) {
            orderBy = ProdutosContract.ProdutoEntry.COLUMN_TITLE + " ASC";
        } else {
            orderBy = ProdutosContract.ProdutoEntry.COLUMN_PRICE + " ASC";
        }

        return new CursorLoader(getContext(), ProdutosContract.ProdutoEntry.CONTENT_URI, projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
         adapter.swapCursor(data);
         progressDialog.dismiss();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
          adapter.swapCursor(null);
    }

    public interface Callback {
        void onItemSelected(Uri uri);
    }

    public void setUseProdutoDestaque(boolean useProdutoDestaque) {
        this.useProdutoDestaque = useProdutoDestaque;

        if (adapter != null) {
            adapter.setUseProdutoDestaque(useProdutoDestaque);
        }
    }

    private View.OnClickListener onClickVerCarrinho() {
        return new Button.OnClickListener() {
           public void onClick(View v) {
               Intent intent = new Intent(getContext(), VisaoCarrinho.class);
               mCarrinho = mCarrinho;
               startActivity(intent);
               Log.d("C1", "Ver carrinho");
           }
        };
    }

    private View.OnClickListener onClickZerarCarrinho() {
        return new Button.OnClickListener() {
            public void onClick(View v) {
                mCarrinho.empty();
                totalValorCarrinho.setText("Total do Carrinho = R$ " + String.format("%.2f", mCarrinho.getValue()));
                Log.d("C2", "Zerar carrinho");
            }
        };
    }

    private View.OnClickListener onClickPagar() {
        return new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("C3", "Pagar");
            }
        };
    }

}
