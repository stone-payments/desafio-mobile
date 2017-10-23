package com.stone.mobile.stonestore.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.stone.mobile.stonestore.Adapters.HistoricoAdapter;
import com.stone.mobile.stonestore.R;
import com.stone.mobile.stonestore.Utils.DataBaseStoneStore;
import com.stone.mobile.stonestore.Utils.TransacaoLocal;

import java.util.List;

public class HistoricoFragment extends Fragment {

    private Context context;

    private ListView listaTransacoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_historico, container, false);

        context = getContext();

        listaTransacoes = layout.findViewById(R.id.listCarrinho);

        new BuscarHistorico().execute();

        return layout;
    }

    //==================================== Inner Classes ===============================================

    class BuscarHistorico extends AsyncTask<String, Void, List<TransacaoLocal>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<TransacaoLocal> doInBackground(String... params) {
            DataBaseStoneStore dataBaseStoneStore = new DataBaseStoneStore(context);
            return dataBaseStoneStore.buscarTransacaoLocal();
        }

        @Override
        protected void onPostExecute(List<TransacaoLocal> transacaoLocalList) {
            super.onPostExecute(transacaoLocalList);

            if (transacaoLocalList != null && !transacaoLocalList.isEmpty()) {
                HistoricoAdapter historicoAdapter = new HistoricoAdapter(context, transacaoLocalList);
                listaTransacoes.setAdapter(historicoAdapter);
            }
        }
    }
}
