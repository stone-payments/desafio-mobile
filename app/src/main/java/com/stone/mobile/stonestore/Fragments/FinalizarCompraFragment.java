package com.stone.mobile.stonestore.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stone.mobile.stonestore.R;
import com.stone.mobile.stonestore.Utils.Conexao;
import com.stone.mobile.stonestore.Utils.DataBaseStoneStore;
import com.stone.mobile.stonestore.Utils.Produto;
import com.stone.mobile.stonestore.Utils.TransacaoLocal;
import com.stone.mobile.stonestore.Utils.TransacaoVenda;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class FinalizarCompraFragment extends Fragment {

    // se clicar em finalizar compra.. limpa a tabela CARRINHO
    // ao finalizar compra envia os dados para servidor e salva local.
    private Context context;

    private EditText tvNumeroCartao;
    private EditText tvDataCartao;
    private EditText tvCVVCartao;
    private EditText tvNomeCartao;

    private Button btnFinalizarCompra;

    private int total = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        total = bundle.getInt("tatal", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_finalizar_compra, container, false);

        tvNumeroCartao = layout.findViewById(R.id.editTextCartao);
        tvDataCartao = layout.findViewById(R.id.editTextData);
        tvCVVCartao = layout.findViewById(R.id.editTextCVV);
        tvNomeCartao = layout.findViewById(R.id.editTextNome);

        btnFinalizarCompra = layout.findViewById(R.id.btnfinalizarComCartao);

        btnFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numeroCartao = tvNumeroCartao.getText().toString();
                String dataCartao = tvDataCartao.getText().toString();
                String cvvCartao = tvCVVCartao.getText().toString();
                String nomeCartao = tvNomeCartao.getText().toString();

                if (!numeroCartao.isEmpty() &&
                    !dataCartao.isEmpty()   &&
                    !cvvCartao.isEmpty()    &&
                    !nomeCartao.isEmpty()      ) {

                    TransacaoVenda transacaoVenda = new TransacaoVenda();
                    transacaoVenda.setCardHolderName(nomeCartao);
                    transacaoVenda.setCardNumber(numeroCartao);
                    transacaoVenda.setCvv(Integer.valueOf(cvvCartao));
                    transacaoVenda.setExpDate(dataCartao);
                    transacaoVenda.setValue(total);

                    // enviar dados e salvar no BD local
                    new FinalizarCompra().execute(transacaoVenda);
                }

            }
        });

        return layout;
    }

//==================================== Inner Classes ===============================================

    class FinalizarCompra extends AsyncTask<TransacaoVenda, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(TransacaoVenda... params) {
            // enviar dados
            // limpar carrinho

            DataBaseStoneStore dataBaseStoneStore = new DataBaseStoneStore(context);
            List<Produto> produtoList = dataBaseStoneStore.buscarProdutosCarrinho();

            for (Produto produtoItem : produtoList) {
                // excluir do carrinho os itens
                dataBaseStoneStore.excluirItemCarrinho(produtoItem);
            }

            // enviar o pedido
            Conexao.makeShopping(params[0]);

            // salvar local
            TransacaoLocal transacaoLocal = new TransacaoLocal();
            transacaoLocal.setValor(params[0].getValue());
            transacaoLocal.setNomeCartao(params[0].getCardHolderName());
            transacaoLocal.setNumCartao(params[0].getCardNumber());

            Calendar calendar = new GregorianCalendar();
            String data = calendar.getTime().toString();
            transacaoLocal.setData(data);

            dataBaseStoneStore.inserirTransacaoLocal(transacaoLocal);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(context, "Compra Realizada com Sucesso!", Toast.LENGTH_SHORT).show();
            // ir para historico
            Activity activity = getActivity();
            if (activity instanceof VotlarAoHistorico) {
                ((VotlarAoHistorico) activity).voltarHistorico();
            }
        }
    }

    public interface VotlarAoHistorico {
        void voltarHistorico();
    }
}
