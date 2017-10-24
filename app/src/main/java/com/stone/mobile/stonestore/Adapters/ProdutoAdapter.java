package com.stone.mobile.stonestore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stone.mobile.stonestore.R;
import com.stone.mobile.stonestore.Utils.Produto;

import java.util.List;

public class ProdutoAdapter extends BaseAdapter {

    List<Produto> produtoLits;
    Context context;

    public ProdutoAdapter(Context context, List<Produto> produtoLits) {
        this.produtoLits = produtoLits;
        this.context = context;
    }

    @Override
    public int getCount() {
        return produtoLits.size();
    }

    @Override
    public Object getItem(int position) {
        return produtoLits.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_produtos, parent, false);

            viewHolder.produtoImageView = (ImageView) convertView.findViewById(R.id.produto_imagem);
            viewHolder.produtoNomeTextView = (TextView) convertView.findViewById(R.id.produto_nome);
            viewHolder.produtoPrecoTextView = (TextView) convertView.findViewById(R.id.produto_preco);
            viewHolder.produtoVendedorTextView = (TextView) convertView.findViewById(R.id.produto_vendedor);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Produto produto = produtoLits.get(position);

        if (produto.getThumbnailHd() != null) {

            Glide.with(context)
                    .load(produto.getThumbnailHd())
                    .into(viewHolder.produtoImageView);
        }

        viewHolder.produtoNomeTextView.setText(produto.getTitle());
        viewHolder.produtoPrecoTextView.setText("" + produto.getPrice());
        viewHolder.produtoVendedorTextView.setText(produto.getSeller());

        return convertView;
    }

    static class ViewHolder {
        ImageView produtoImageView;
        TextView produtoNomeTextView;
        TextView produtoPrecoTextView;
        TextView produtoVendedorTextView;
    }

}
