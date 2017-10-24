package com.stone.mobile.stonestore.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.mobile.stonestore.Adapters.CarrinhoAdapter;
import com.stone.mobile.stonestore.R;
import com.stone.mobile.stonestore.Utils.DataBaseStoneStore;
import com.stone.mobile.stonestore.Utils.Produto;

import java.util.List;

public class CarrinhoFragment extends Fragment {

    private Context context;

    private ListView listaItensCarrinho;
    private TextView tvTotal;
    private Button btnFinalizarCompra;

    private int totalCompra = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_carrinho, container, false);

        context = getContext();

        listaItensCarrinho = layout.findViewById(R.id.listCarrinho);
        tvTotal = layout.findViewById(R.id.tvTotalCompra);
        btnFinalizarCompra = layout.findViewById(R.id.btnFinalizarCompra);

        new BuscarItensCarrinho().execute();

        btnFinalizarCompra.setOnClickListener(onClickFinalizarCompra());

        return layout;
    }

    private AdapterView.OnItemClickListener onClickProdutoItem() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = (Produto) parent.getItemAtPosition(position);

                // remover do carrinho
                new RemoveItem().execute(produto);
            }
        };
    }

    private View.OnClickListener onClickFinalizarCompra() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ir pra tela pra preencher dados
                Activity activity = getActivity();
                if (activity instanceof CarrinhoFinalizarCompra) {
                    ((CarrinhoFinalizarCompra) activity).finalizarCompraFragment(totalCompra);
                }

            }
        };
    }

//==================================== Inner Classes ===============================================

    class BuscarItensCarrinho extends AsyncTask<String, Void, List<Produto>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Produto> doInBackground(String... params) {
            DataBaseStoneStore dataBaseStoneStore = new DataBaseStoneStore(context);
            return dataBaseStoneStore.buscarProdutosCarrinho();
        }

        @Override
        protected void onPostExecute(List<Produto> produtoList) {
            super.onPostExecute(produtoList);

            if (produtoList != null) {
                // fazer somatorio dos itens
                int somaTotal = 0;
                for (Produto produtoItem : produtoList) {
                    somaTotal += produtoItem.getPrice();
                }
                totalCompra = somaTotal;
                String total = "Total: R$ " + somaTotal;
                tvTotal.setText(total);

                CarrinhoAdapter carrinhoAdapter = new CarrinhoAdapter(context, produtoList);
                listaItensCarrinho.setAdapter(carrinhoAdapter);
                listaItensCarrinho.setOnItemClickListener(onClickProdutoItem());
            }
        }
    }

    class RemoveItem extends AsyncTask<Produto, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Produto... params) {
            DataBaseStoneStore dataBaseStoneStore = new DataBaseStoneStore(context);
            dataBaseStoneStore.excluirItemCarrinho(params[0]);

            new BuscarItensCarrinho().execute();

            return null;

        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);


            Toast.makeText(context, "Item removido!", Toast.LENGTH_SHORT).show();
        }
    }

    public interface CarrinhoFinalizarCompra {
        void finalizarCompraFragment(int totalCompra);
    }
}
