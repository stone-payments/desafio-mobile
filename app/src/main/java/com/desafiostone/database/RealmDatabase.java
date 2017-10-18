package com.desafiostone.database;

import android.content.Context;

import com.desafiostone.domain.Products;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Filipi Andrade on 17-Oct-17.
 */

public class RealmDatabase {

    private static RealmDatabase mInstance;

    private Realm realm;
    private RealmConfiguration realmConfiguration;

    private Context context;

    private RealmDatabase() {
    }

    public static RealmDatabase getInstance() {
        if (mInstance == null) {
            mInstance = new RealmDatabase();
        }
        return mInstance;
    }

    public void setContext(Context context) {
        mInstance.context = context;
        Realm.init(context);
        mInstance.realmConfiguration = new RealmConfiguration.Builder()
                .name("desafiostone.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.compactRealm(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);
    }

    public void addCart(Products p) {
        realm = Realm.getInstance(realmConfiguration);
        realm.beginTransaction();
        Products products = realm.createObject(Products.class);
        products.setDate(p.getDate());
        products.setPrice(p.getPrice());
        products.setSeller(p.getSeller());
        products.setThumbnailHd(p.getThumbnailHd());
        products.setZipcode(p.getZipcode());
        products.setTitle(p.getTitle());
        realm.commitTransaction();
    }

    public RealmResults<Products> getCart() {
        realm = Realm.getInstance(realmConfiguration);
        return realm.where(Products.class).findAll();
    }
}
