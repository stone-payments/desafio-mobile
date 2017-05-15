package com.jademcosta.starstore.cart;


import com.jademcosta.starstore.entity.Item;

import java.util.List;

public interface CartContract {

    interface View {

        void setCartItems(List<Item> items);
        void setCartItemsTotalPrice(String sum);

        interface Presenter {
            void onCreate();
        }
    }

    interface Model {

        List<Item> getCartItemsList();

        interface Presenter {

        }
    }
}
