package com.jademcosta.starstore.itemsList;


import com.jademcosta.starstore.entity.Item;
import com.jademcosta.starstore.network.ItemsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsListModel implements ItemsListContract.Model {

    private Presenter presenter;

    private ItemsApi api;

    public ItemsListModel(ItemsApi api) {
        this.api = api;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getItemsList() {
        api.fetchItems(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                presenter.onItemsListFetched(response.body());
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                presenter.onItemsListFetchFailure();
            }
        });
    }
}
