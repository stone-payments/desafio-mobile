package br.com.tiago.desafiomobile.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.tiago.desafiomobile.R;
import br.com.tiago.desafiomobile.interactor.impl.ProductInteractorImpl;
import br.com.tiago.desafiomobile.model.ProductModel;
import br.com.tiago.desafiomobile.presenter.ProductListPresenter;
import br.com.tiago.desafiomobile.presenter.impl.ProductListPresenterImpl;
import br.com.tiago.desafiomobile.ui.adapter.ProductRecyclerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public class ProductListFragment extends BaseFragment {

    private static String TAG = ProductListFragment.class.getSimpleName();
    private ProductListPresenter productListPresenter;
    private ProductRecyclerAdapter productRecyclerAdapter;
    private ProductModel productModel;

    @BindView(R.id.product_recycler_view)
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: inicio");

        View inflate = inflater.inflate(R.layout.product_recycler_view_fragment, container, false);
        ButterKnife.bind(this, inflate);

        productListPresenter = new ProductListPresenterImpl(this, new ProductInteractorImpl());
        productRecyclerAdapter = new ProductRecyclerAdapter(productListPresenter);
        recyclerView.setAdapter(productRecyclerAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));

        Log.d(TAG, "onCreateView: fim");
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: inicio");

        if (getProductList().isEmpty()) {
            productListPresenter.getProductList();
        } else {
            addAll(getProductList());
        }

        Log.d(TAG, "onActivityCreated: fim");
    }

    public void addAll(List<ProductModel> products) {
        Log.d(TAG, "addAll: inicio");
        getProductList().addAll(products);
        productRecyclerAdapter.addAll(products);
        Log.d(TAG, "addAll: fim");
    }

    public void onItemClick(int position) {
        productModel = productRecyclerAdapter.getItem(position);
        Log.d(TAG, "onItemClick: ".concat(productModel.toString()));

        getCartList().add(productModel);
        getTransactionModel().setValueDouble(productModel.getPrice());

        CartListFragment.getCartRecyclerAdapter().notifyDataSetChanged();
        showToast("Produto adicionado ao carrinho!", 0);

    }
}
