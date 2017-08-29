package com.germano.desafiostone.presenters;

import com.germano.desafiostone.models.Product;
import com.germano.desafiostone.views.ProductListView;

/**
 * Created by germano on 26/08/17.
 */

public interface ProductListPresenter {

    void initView(ProductListView productListView);
    void onProductClicked(Product product);
    void getProductList();

}
