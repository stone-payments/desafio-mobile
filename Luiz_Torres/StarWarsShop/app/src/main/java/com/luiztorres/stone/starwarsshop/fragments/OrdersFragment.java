package com.luiztorres.stone.starwarsshop.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.luiztorres.stone.starwarsshop.R;
import com.luiztorres.stone.starwarsshop.adapters.OrdersListAdapter;
import com.luiztorres.stone.starwarsshop.adapters.ShopListAdapter;
import com.luiztorres.stone.starwarsshop.extras.Connection;
import com.luiztorres.stone.starwarsshop.extras.Item;
import com.luiztorres.stone.starwarsshop.extras.Order;
import com.luiztorres.stone.starwarsshop.extras.StorageManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dindal on 31/12/2016.
 */

public class OrdersFragment extends Fragment {
    private List<Order> orders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle_orders);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        new MakeOrdersRequest().execute();
    }

    private class MakeOrdersRequest extends AsyncTask<Void, Integer, List<Order>> {
        protected List<Order> doInBackground(Void... urls) {
            List<Order> list;
            list = StorageManager.getInstance(getActivity()).getAllOrders();
            return list;
        }

        @Override
        protected void onPostExecute(List<Order> list) {
            orders = list;
            FeedOrders();
        }
    }

    private void FeedOrders()
    {
        RecyclerView rv_listOfCards = (RecyclerView) getActivity().findViewById(R.id.rv_listOrders);
        rv_listOfCards.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_listOfCards.setLayoutManager(layoutManager);

        OrdersListAdapter mAdapter = new OrdersListAdapter(getActivity(), orders);
        rv_listOfCards.setAdapter(mAdapter);
    }
}
