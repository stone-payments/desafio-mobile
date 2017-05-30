package com.hernand.starwars.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hernand.starwars.R;
import com.hernand.starwars.vo.ProductVO;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nando on 27/05/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private List<ProductVO> productList;
    private OnItemClickListener onItemClickListener;
    private Context ctx;
    public RecyclerViewAdapter(List<ProductVO> produtos, Context ctx) {
        this.productList = produtos;
        this.ctx = ctx;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        ProductVO item = productList.get(position);

        holder.productname.setText(item.getTitle());
        String preco = String.format("RS %d",item.getPrice());

        holder.productprice.setText(preco);
        holder.productseller.setText(item.getSeller());
        holder.image.setImageBitmap(null);
        Picasso.with(holder.image.getContext()).load(item.getThumbnailHd()).into(holder.image);
        holder.itemView.setTag(item);

    }

    @Override public int getItemCount() {
        return productList.size();
    }

    @Override public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (ProductVO) v.getTag());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView productname;
        public TextView productprice;
        public TextView productseller;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            productname = (TextView) itemView.findViewById(R.id.productname);
            productprice = (TextView) itemView.findViewById(R.id.productprice);
            productseller = (TextView) itemView.findViewById(R.id.productseller);

        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, ProductVO viewModel);

    }
}
