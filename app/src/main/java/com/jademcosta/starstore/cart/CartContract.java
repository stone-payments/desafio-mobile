package com.jademcosta.starstore.cart;


import android.content.Context;

import com.jademcosta.starstore.entity.Item;

import java.util.List;

public interface CartContract {

    interface View {

        void setCartItems(List<Item> items);
        void setCartItemsTotalPrice(String sum);
        void showEmptyState();

        interface Presenter {
            void onCreate();
            void checkoutClicked(Context context);
        }
    }

    interface Model {

        List<Item> getCartItemsList();

        interface Presenter {

        }
    }
}
