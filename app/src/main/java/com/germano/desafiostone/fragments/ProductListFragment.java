package com.germano.desafiostone.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.germano.desafiostone.Constants;
import com.germano.desafiostone.R;
import com.germano.desafiostone.activities.ProductDetailActivity;
import com.germano.desafiostone.adapters.ProductAdapter;
import com.germano.desafiostone.models.Product;
import com.germano.desafiostone.presenters.ProductListPresenter;
import com.germano.desafiostone.presenters.ProductListPresenterImpl;
import com.germano.desafiostone.utils.RequestCode;
import com.germano.desafiostone.views.ProductListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by germano on 23/08/17.
 */

public class ProductListFragment extends Fragment implements ProductListView{

    @BindView(R.id.recyclerview_product)
    RecyclerView mRecyclerViewProduct;

    ProductListPresenter mEventHandler;

    ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, view);
        mEventHandler = new ProductListPresenterImpl(getContext());
        mEventHandler.initView(this);
        return view;
    }

    @Override
    public void contentView() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.wait));
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.hide();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setupRecyclerView(List<Product> arrayList) {
        ProductAdapter recyclerViewAdapter = new ProductAdapter(getActivity(), arrayList, null);
        recyclerViewAdapter.setOnProductClicked(mEventHandler::onProductClicked);
        mRecyclerViewProduct.setAdapter(recyclerViewAdapter);
        mRecyclerViewProduct.setLayoutManager(new LinearLayoutManager(getActivity(),
                                                LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void showProductDetail(Product product) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        intent.putExtra(Constants.PRODUCT, product);
        startActivity(intent);
    }
}
