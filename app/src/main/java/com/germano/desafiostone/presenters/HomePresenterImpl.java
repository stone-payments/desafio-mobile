package com.germano.desafiostone.presenters;

import com.germano.desafiostone.views.HomeActivityView;

/**
 * Created by germano on 23/08/17.
 */

public class HomePresenterImpl implements HomePresenter {

    HomeActivityView mView;

    @Override
    public void initView(HomeActivityView homeActivityView) {
        this.mView = homeActivityView;

        if(homeActivityView != null){
            mView.setupBottomBar();
            mView.contentView();
        }
    }

}
