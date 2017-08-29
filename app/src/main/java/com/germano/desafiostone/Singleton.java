package com.germano.desafiostone;

import android.app.Application;

import com.germano.desafiostone.models.Product;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by germano on 27/08/17.
 */

// I'll use Singleton only to simulate a session service

public class Singleton extends Application {

    static Singleton mInstance;
    private ArrayList<Product> productList = new ArrayList<>();



    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.
                Builder().
                build();
        Realm.setDefaultConfiguration(config);
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public static Singleton getInstance() {
        if (mInstance == null) {
            mInstance = new Singleton();
        }

        return mInstance;
    }
}
