package com.luiztorres.stone.starwarsshop.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luiztorres.stone.starwarsshop.R;
import com.luiztorres.stone.starwarsshop.adapters.CartListAdapter;
import com.luiztorres.stone.starwarsshop.extras.CartManager;
import com.luiztorres.stone.starwarsshop.extras.ScreenManager;

/**
 * Created by Dindal on 31/12/2016.
 */

public class CartFragment extends Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle_cart);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        CartManager.getInstance(getActivity()).FeedList();

        RelativeLayout im_btn_check = (RelativeLayout) getActivity().findViewById(R.id.btn_cart_checkout);
        im_btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.getInstance(getActivity()).Replace(new CheckoutFragment());
            }
        });
    }
}
