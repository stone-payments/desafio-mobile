package com.jademcosta.starstore.network;


import com.jademcosta.starstore.entity.Item;

import java.util.List;

import retrofit2.Callback;

public class ItemsApi {

    private static final String BASE_URL = "https://raw.githubusercontent.com";

    private ItemEndpoints endpoints;

    public ItemsApi() {
        endpoints =
            new NetworkStackBuilder(BASE_URL).getServiceGenerator().create(ItemEndpoints.class);
    }

    public void fetchItems(Callback<List<Item>> callback) {
        endpoints.listItems().enqueue(callback);
    }
}
