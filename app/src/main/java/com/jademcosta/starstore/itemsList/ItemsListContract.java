package com.jademcosta.starstore.itemsList;


import com.jademcosta.starstore.entity.Item;

import java.util.List;

public interface ItemsListContract {

    interface View {

        void setPresenter(Presenter presenter);
        void showLoading();
        void hideLoading();
        void hideList();
        void showList();
        void setListItems(List<Item> items);

        interface Presenter {
            void onCreate();
            void setView(View view);
        }
    }

    interface Model {

        void setPresenter(Presenter presenter);
        void getItemsList();

        interface Presenter {
            void setModel(Model model);
            void onItemsListFetchFailure();
            void onItemsListFetched(List<Item> items);
        }
    }
}
