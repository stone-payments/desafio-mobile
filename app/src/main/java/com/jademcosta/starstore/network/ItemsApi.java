package com.jademcosta.starstore.network;


import com.jademcosta.starstore.entity.Item;

import java.util.List;

import retrofit2.Callback;

public class ItemsApi {

    private ItemEndpoints endpoints;

    public ItemsApi() {
        endpoints =
            NetworkStackBuilder.getInstance().getServiceGenerator().create(ItemEndpoints.class);
    }

    public void fetchItems(Callback<List<Item>> callback) {
        endpoints.listItems().enqueue(callback);
    }
}
