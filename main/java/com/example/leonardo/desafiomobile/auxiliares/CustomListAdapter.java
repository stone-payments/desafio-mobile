package com.example.leonardo.desafiomobile.auxiliares;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leonardo.desafiomobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 02/04/2017.
 */
public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder>{

        private Context context;
        private List<Produto> produtos;

        public CustomListAdapter(Context context, ArrayList<Produto> produtos) {
            this.context = context;
            this.produtos = produtos;
        }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(context)
                .load(produtos.get(position).getThumbnailHd())
                .into(holder.fotoItem);

        holder.nome.setText(produtos.get(position).getTitle());
        holder.preço.setText("R$ " + produtos.get(position).getPrice());
        holder.vendedor.setText(produtos.get(position).getSeller());



    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView fotoItem;
            TextView nome;
            TextView preço;
            TextView vendedor;

            public ViewHolder(View holder) {
                super(holder);
                nome = (TextView) holder.findViewById(R.id.nome);
                preço = (TextView) holder.findViewById(R.id.preço);
                vendedor = (TextView) holder.findViewById(R.id.vendedor);
                fotoItem = (ImageView) holder.findViewById(R.id.fotoItem);
            }
    }
}