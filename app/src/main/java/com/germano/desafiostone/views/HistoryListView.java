package com.germano.desafiostone.views;

import com.germano.desafiostone.models.Product;

import java.util.List;

import io.realm.RealmQuery;

/**
 * Created by germano on 28/08/17.
 */

public interface HistoryListView {

    void contentView();
    void setupRecyclerView(RealmQuery realmQuery);
    void showLoading();
    void hideLoading();
    void showEmptyHistory();
}
