package com.stone.lfernandosantos.storewars.controlers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stone.lfernandosantos.storewars.R;
import com.stone.lfernandosantos.storewars.models.Product;

import java.util.List;

/**
 * Created by lf.fernandodossantos on 21/05/17.
 */

public class ListComprasAdapter extends BaseAdapter{

    private List<Product> products;
    private Activity activity;
    private ImageView imgCompra;
    private TextView txtTitle;
    private TextView txtSeller;
    private TextView txtPrice;

    public ListComprasAdapter(List<Product> products, Activity activity) {
        this.products = products;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v =  activity.getLayoutInflater().inflate(R.layout.item_carrinho_adapter, parent, false);

        imgCompra = (ImageView) v.findViewById(R.id.imageViewComprasAdp);
        txtTitle = (TextView) v.findViewById(R.id.txtTitleProductAdp);
        txtSeller = (TextView) v.findViewById(R.id.txtSellerProductAdp);
        txtPrice = (TextView) v.findViewById(R.id.txtPriceComprasAdp);

        Glide.with(activity).load(products.get(position).thumbnailHd).into(imgCompra);
        txtTitle.setText(products.get(position).title);
        txtSeller.setText(products.get(position).seller);
        txtPrice.setText(products.get(position).getPrice());

        return v;
    }
}
