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
import com.luiztorres.stone.starwarsshop.extras.ScreenManager;
import com.luiztorres.stone.starwarsshop.fragments.CheckoutFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by Dindal on 03/01/2017.
 */

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder>{
        private Context ctx;
        private List<Item> itemCartList;

        public CartListAdapter(Context c, List<Item> list)
        {
            this.ctx = c;
            this.itemCartList = list;

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
            public TextView name, seller, price, count;
            public ImageView thumbnail, add, remove;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.iv_cart_layout_item_name);
                seller = (TextView) view.findViewById(R.id.tv_cart_layout_item_seller);
                price = (TextView) view.findViewById(R.id.iv_cart_layout_item_price);
                thumbnail = (ImageView) view.findViewById(R.id.iv_cart_layout_item_image);
                count = (TextView) view.findViewById(R.id.tv_cart_layout_totalCount);
                add = (ImageView) view.findViewById(R.id.iv_cart_layout_add_item);
                remove = (ImageView) view.findViewById(R.id.iv_cart_layout_remove_item);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_cart_layout, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final Item i = itemCartList.get(position);
            holder.name.setText(i.getName());
            holder.seller.setText(i.getSeller());
            holder.price.setText("R$:"+i.getPrice());
            ImageLoader.getInstance().displayImage(i.getImageURL(), holder.thumbnail);
            holder.count.setText(""+i.getCount());
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartManager.getInstance(ctx).AddItem(i);
                }
            });
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartManager.getInstance(ctx).RemoveItem(i);
                }
            });
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public int getItemCount() {
            return itemCartList.size();
        }
}
