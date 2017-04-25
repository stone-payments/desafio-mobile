package com.example.leonardo.desafiomobile.auxiliares;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leonardo.desafiomobile.Activities.Carrinho;
import com.example.leonardo.desafiomobile.Objetos.Produto;
import com.example.leonardo.desafiomobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 02/04/2017.
 */
public class CustomListAdapter2 extends RecyclerView.Adapter<CustomListAdapter2.ViewHolder>{

        private Context context;
        private List<Produto> produtos;

        public CustomListAdapter2(Context context, ArrayList<Produto> produtos) {
            this.context = context;
            this.produtos = produtos;
        }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_carrinho, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso.with(context)
                .load(produtos.get(position).getThumbnailHd())
                .into(holder.fotoItem);

        holder.nome.setText(produtos.get(position).getTitle());
        holder.preço.setText("R$ " + produtos.get(position).getPrice1());
        holder.qtd.setText("Quantidade: " + produtos.get(position).getQuantidade());
        holder.vendedor.setText(produtos.get(position).getSeller1());
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
                Intent i = new Intent(context,Carrinho.class);
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView fotoItem;
            TextView nome;
            TextView preço;
            TextView qtd;
            TextView vendedor;
            Button b1;

            public ViewHolder(View holder) {
                super(holder);
                nome = (TextView) holder.findViewById(R.id.nomeC);
                preço = (TextView) holder.findViewById(R.id.preçoC);
                qtd = (TextView) holder.findViewById(R.id.qtdC);
                fotoItem = (ImageView) holder.findViewById(R.id.fotoC);
                b1 = (Button) holder.findViewById(R.id.excluirC);
                vendedor = (TextView) holder.findViewById(R.id.vendedorC);
            }
    }

    public void removeItem(int position){
        produtos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, produtos.size());
    }
}