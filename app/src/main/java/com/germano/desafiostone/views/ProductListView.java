package com.germano.desafiostone.views;

import com.germano.desafiostone.models.Product;

import java.util.List;

/**
 * Created by germano on 25/08/17.
 */

public interface ProductListView {

    void contentView();
    void setupRecyclerView(List<Product> products);
    void showProductDetail(Product product);
    void showLoading();
    void hideLoading();
    void showError(String msg);
}
