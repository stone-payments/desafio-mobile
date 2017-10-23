package com.stone.mobile.stonestore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stone.mobile.stonestore.R;
import com.stone.mobile.stonestore.Utils.TransacaoLocal;

import java.util.List;

public class HistoricoAdapter extends BaseAdapter {

    List<TransacaoLocal> transacaoLocalList;
    Context context;

    public HistoricoAdapter(Context context, List<TransacaoLocal> transacaoLocalList) {
        this.transacaoLocalList = transacaoLocalList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return transacaoLocalList.size();
    }

    @Override
    public Object getItem(int position) {
        return transacaoLocalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_historico, parent, false);

            viewHolder.tvData = convertView.findViewById(R.id.tvData);
            viewHolder.tvValor = convertView.findViewById(R.id.tvValor);
            viewHolder.tvNomeCartao = convertView.findViewById(R.id.tvNomeCartao);
            viewHolder.tvNumCartao = convertView.findViewById(R.id.tvNumCartao);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TransacaoLocal transacaoLocal = transacaoLocalList.get(position);

        viewHolder.tvData.setText(transacaoLocal.getData());
        viewHolder.tvValor.setText("" + transacaoLocal.getValor());
        viewHolder.tvNomeCartao.setText(transacaoLocal.getNomeCartao());
        viewHolder.tvNumCartao.setText(transacaoLocal.getNumCartao());

        return convertView;
    }

    static class ViewHolder {
        TextView tvValor;
        TextView tvNomeCartao;
        TextView tvNumCartao;
        TextView tvData;
    }

}
