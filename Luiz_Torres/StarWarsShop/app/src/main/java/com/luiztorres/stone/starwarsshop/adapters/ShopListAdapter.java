package com.luiztorres.stone.starwarsshop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luiztorres.stone.starwarsshop.R;
import com.luiztorres.stone.starwarsshop.extras.CartManager;
import com.luiztorres.stone.starwarsshop.extras.Item;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by Dindal on 31/12/2016.
 */

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.MyViewHolder>{
    private Context ctx;
    private List<Item> itemList;

    public ShopListAdapter(Context c, List<Item> list)
    {
        this.ctx = c;
        this.itemList = list;

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_error)
                .showImageOnFail(R.drawable.ic_error)
                .showImageOnLoading(R.drawable.ic_loading)
                .cacheInMemory(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, seller, price;
        public ImageView thumbnail, addCart;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.iv_shop_layout_item_name);
            seller = (TextView) view.findViewById(R.id.iv_shop_layout_item_seller);
            price = (TextView) view.findViewById(R.id.iv_shop_layout_item_price);
            thumbnail = (ImageView) view.findViewById(R.id.iv_shop_layout_item_image);
            addCart = (ImageView) view.findViewById(R.id.iv_shop_layout_add_cart);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_shop_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Item i = itemList.get(position);
        holder.name.setText(i.getName());
        holder.seller.setText(i.getSeller());
        holder.price.setText("R$:"+i.getPrice());
        ImageLoader.getInstance().displayImage(i.getImageURL(), holder.thumbnail);
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    CartManager.getInstance(ctx).AddItem(i);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
