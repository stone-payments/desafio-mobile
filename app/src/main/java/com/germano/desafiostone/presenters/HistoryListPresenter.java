package com.germano.desafiostone.presenters;

import com.germano.desafiostone.views.HistoryListView;


/**
 * Created by germano on 28/08/17.
 */

public interface HistoryListPresenter {

    void initView(HistoryListView historyListView);

    void getHistoryList();
}
