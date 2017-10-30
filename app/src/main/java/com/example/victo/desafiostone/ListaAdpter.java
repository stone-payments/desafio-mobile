package com.example.victo.desafiostone;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


/**
 * Created by victo on 19/10/2017.
 */



public class ListaAdpter extends ArrayAdapter<Produto> {

        ArrayList<Produto> produtos;
        Context context;
        int resource;


        public ListaAdpter(Context context, int resource, ArrayList<Produto> produtos) {
            super(context, resource, produtos);
            this.produtos = produtos;
            this.context = context;
            this. resource = resource;

        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent){

            if(convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) getContext()
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.custom_list_layout, null, true);
            }


            Produto produto = getItem(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewProduct);
            Picasso.with(context).load(produto.getImage()).into(imageView);

            TextView txtTitle = (TextView) convertView.findViewById(R.id.titleCarrinho);
            txtTitle.setText(produto.getTitle());

            TextView txtPrice = (TextView) convertView.findViewById(R.id.etPrice);
            txtPrice.setText(produto.getPrice());

            TextView txtSeller = (TextView) convertView.findViewById(R.id.txtSeller);
            txtSeller.setText(produto.getSeller());

            return convertView;
        }
    

    }
