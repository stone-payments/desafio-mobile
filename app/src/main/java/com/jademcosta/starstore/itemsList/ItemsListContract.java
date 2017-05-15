package com.jademcosta.starstore.itemsList;


import com.jademcosta.starstore.entity.Item;

import java.util.List;

public interface ItemsListContract {

    interface View {

        void showLoading();
        void hideLoading();
        void hideList();
        void showList();
        void setListItems(List<Item> items);
        void informItemAddedToCart();

        interface Presenter {
            void onCreate();
            void itemClicked(Item item);
        }
    }

    interface Model {

        void getItemsList();
        void addItemToCart(Item item);

        interface Presenter {
            void onItemsListFetchFailure();
            void onItemsListFetched(List<Item> items);
        }
    }
}
