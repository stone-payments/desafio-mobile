package com.germano.desafiostone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.germano.desafiostone.Constants;
import com.germano.desafiostone.R;
import com.germano.desafiostone.adapters.HistoryAdapter;
import com.germano.desafiostone.presenters.HistoryListPresenter;
import com.germano.desafiostone.presenters.HistoryListPresenterImpl;
import com.germano.desafiostone.utils.RequestCode;
import com.germano.desafiostone.views.HistoryListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmQuery;

/**
 * Created by germano on 23/08/17.
 */

public class HistoryListFragment extends Fragment implements HistoryListView{

    @BindView(R.id.recyclerview_history)
    RecyclerView mRecyclerViewHistory;

    @BindView(R.id.textview_empty_history)
    TextView mTextviewEmptyHistory;

    HistoryListPresenter mEventHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        ButterKnife.bind(this, view);
        mEventHandler = new HistoryListPresenterImpl(getContext());
        mEventHandler.initView(this);
        mEventHandler.getHistoryList();
        return view;
    }

    @Override
    public void contentView() {

    }

    @Override
    public void setupRecyclerView(RealmQuery realmQuery) {
        HistoryAdapter recyclerViewAdapter = new HistoryAdapter(getActivity(), realmQuery);
        mRecyclerViewHistory.setAdapter(recyclerViewAdapter);
        mRecyclerViewHistory.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void showLoading() {
        //Fast request, loading not needed...
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showEmptyHistory() {
        mTextviewEmptyHistory.setVisibility(View.VISIBLE);
    }
}
