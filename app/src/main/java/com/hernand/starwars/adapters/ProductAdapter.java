package com.hernand.starwars.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hernand.starwars.R;
import com.hernand.starwars.vo.ProductVO;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nando on 28/05/2017.
 */

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<ProductVO> lista;

    public ProductAdapter(Context context, List<ProductVO> lista){
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


        view = LayoutInflater.from(this.context).inflate(R.layout.item_produto, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.image);
        ProductVO currentListData = (ProductVO)getItem(position);

        ((TextView)view.findViewById(R.id.productname)).setText(currentListData.getTitle());
        ((TextView)view.findViewById(R.id.productseller)).setText(currentListData.getSeller());
        String preco = String.format("RS %d",currentListData.getPrice());
        ((TextView)view.findViewById(R.id.productprice)).setText(preco);

        Picasso.with(imageView.getContext()).load(currentListData.getThumbnailHd()).into(imageView);
        view.setTag(currentListData);


        return view;
    }
}

