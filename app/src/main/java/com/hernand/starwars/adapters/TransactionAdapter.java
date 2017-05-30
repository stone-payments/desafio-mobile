package com.hernand.starwars.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hernand.starwars.R;
import com.hernand.starwars.model.Transact;
import com.hernand.starwars.vo.TransactionVO;

import java.util.List;

/**
 * Created by Nando on 28/05/2017.
 */

public class TransactionAdapter extends BaseAdapter {

    private Context context;
    private List<Transact> lista;

    public TransactionAdapter(Context context, List<Transact> lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        view = LayoutInflater.from(this.context).inflate(R.layout.item_transacao, null);

        Transact currentListData = (Transact)getItem(position);

        ((TextView)view.findViewById(R.id.tvDataHoraTransacao)).setText(currentListData.getDataHora());
        ((TextView)view.findViewById(R.id.tvValor)).setText(String.format("R$ %s",currentListData.getValor().toString()));

        ((TextView)view.findViewById(R.id.tvNumeroCartao)).setText(String.format("****-****-****-%s",currentListData.getNumeroCartao()));
        ((TextView)view.findViewById(R.id.tvNomePortador)).setText(currentListData.getNomePortador());

        view.setTag(currentListData);


        return view;
    }
}

