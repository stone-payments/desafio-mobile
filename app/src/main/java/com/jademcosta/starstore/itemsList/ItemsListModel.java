package com.jademcosta.starstore.itemsList;


import com.jademcosta.starstore.database.ItemsRepository;
import com.jademcosta.starstore.entity.Item;
import com.jademcosta.starstore.network.ItemsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsListModel implements ItemsListContract.Model {

    private Presenter presenter;

    private ItemsApi api;
    private ItemsRepository repository;

    public ItemsListModel(ItemsApi api, ItemsRepository repository) {
        this.api = api;
        this.repository = repository;
    }

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

    @Override
    public void addItemToCart(Item item) {
        repository.add(item);
    }
}
