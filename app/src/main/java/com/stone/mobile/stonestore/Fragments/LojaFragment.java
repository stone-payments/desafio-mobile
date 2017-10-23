package com.stone.mobile.stonestore.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stone.mobile.stonestore.Adapters.ProdutoAdapter;
import com.stone.mobile.stonestore.R;
import com.stone.mobile.stonestore.Utils.Conexao;
import com.stone.mobile.stonestore.Utils.DataBaseStoneStore;
import com.stone.mobile.stonestore.Utils.Produto;

import java.util.List;

public class LojaFragment extends Fragment {

    ListView listaProdutos;
    ProgressBar progressBarBuscar;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_loja, container, false);

        context = getContext();

        listaProdutos = (ListView) layout.findViewById(R.id.lista_produtos);
        progressBarBuscar = (ProgressBar) layout.findViewById(R.id.progressBar_buscar);

        new BuscarProdutos().execute();

        return layout;
    }

//==================================== Inner Classes ===============================================

    class BuscarProdutos extends AsyncTask<String, Void, List<Produto>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBarBuscar.setVisibility(View.VISIBLE);
            listaProdutos.setVisibility(View.GONE);
        }

        @Override
        protected List<Produto> doInBackground(String... params) {
            return Conexao.buscarProdutos();
        }

        @Override
        protected void onPostExecute(List<Produto> produtoList) {
            super.onPostExecute(produtoList);
            progressBarBuscar.setVisibility(View.GONE);
            listaProdutos.setVisibility(View.VISIBLE);

            if (produtoList != null) {
                ProdutoAdapter produtoAdapter = new ProdutoAdapter(context, produtoList);
                listaProdutos.setAdapter(produtoAdapter);
                listaProdutos.setOnItemClickListener(onClickProdutoItem());
            }
        }
    }

    private AdapterView.OnItemClickListener onClickProdutoItem() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = (Produto) parent.getItemAtPosition(position);

                Toast.makeText(context, "Item adicionado!", Toast.LENGTH_SHORT).show();

                // adicionar ao carrinho
                DataBaseStoneStore dataBaseStoneStore = new DataBaseStoneStore(context);
                dataBaseStoneStore.inserirItemCarrinho(produto);
            }
        };
    }

}
