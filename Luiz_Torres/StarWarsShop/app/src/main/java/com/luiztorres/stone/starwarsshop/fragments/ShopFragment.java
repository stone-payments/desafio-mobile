package com.luiztorres.stone.starwarsshop.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.luiztorres.stone.starwarsshop.extras.Connection;
import com.luiztorres.stone.starwarsshop.extras.Item;
import com.luiztorres.stone.starwarsshop.R;
import com.luiztorres.stone.starwarsshop.adapters.ShopListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dindal on 31/12/2016.
 */

public class ShopFragment extends Fragment {
    private ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MakeItemsRequest();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle_shop);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        return view;
    }

    private void MakeItemsRequest()
    {
        String url = "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json";
        String tag_json_array = "json_array_req";

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response != null) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject objeto = response.getJSONObject(i);
                                    Item item = new Item(objeto.getString("title"), objeto.getString("seller"),
                                            objeto.getString("thumbnailHd"), objeto.getDouble("price"));
                                    items.add(item);
                                }

                                OnReceived();
                            }
                            catch (JSONException e)
                            {
                                Toast.makeText(getActivity(), R.string.error_response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), R.string.error_connection, Toast.LENGTH_SHORT).show();
            }
        });

        Connection.getInstance(getActivity()).addToRequestQueue(req, tag_json_array);
    }

    private void OnReceived()
    {
        RecyclerView rv_listOfCards = (RecyclerView) getActivity().findViewById(R.id.rv_shop_listCards);
        rv_listOfCards.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_listOfCards.setLayoutManager(layoutManager);

        ShopListAdapter mAdapter = new ShopListAdapter(getActivity(), items);
        rv_listOfCards.setAdapter(mAdapter);
    }
}
