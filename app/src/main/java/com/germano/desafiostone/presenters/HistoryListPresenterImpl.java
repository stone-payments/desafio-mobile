package com.germano.desafiostone.presenters;

import android.content.Context;

import com.germano.desafiostone.services.HistoryRealm;
import com.germano.desafiostone.services.HistoryRealmImpl;
import com.germano.desafiostone.views.HistoryListView;

/**
 * Created by germano on 28/08/17.
 */

public class HistoryListPresenterImpl implements HistoryListPresenter {

    HistoryListView mView;
    HistoryRealm mRealm;

    public HistoryListPresenterImpl(Context context) {
        mRealm = new HistoryRealmImpl(context);
    }

    @Override
    public void initView(HistoryListView historyListView) {
        this.mView = historyListView;

        if(mView != null){
            mView.contentView();
        }
    }

    @Override
    public void getHistoryList() {
        if(mRealm.isEmpty()) {
            mView.showEmptyHistory();
            return;
        }

        mView.setupRecyclerView(mRealm.getAll());
    }
}
