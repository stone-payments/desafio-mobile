package com.desafiostone.database;

import android.content.Context;

import com.desafiostone.domain.Products;
import com.desafiostone.domain.Purchase;
import com.desafiostone.domain.Transaction;

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

    public void removeFromCart(int position) {
        realm = Realm.getInstance(realmConfiguration);
        realm.beginTransaction();
        realm.where(Products.class).findAll().deleteFromRealm(position);
        realm.commitTransaction();
    }

    public void removeAllItemFromCart() {
        realm = Realm.getInstance(realmConfiguration);
        realm.beginTransaction();
        realm.where(Products.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    public boolean isEmptyCart() {
        realm = Realm.getInstance(realmConfiguration);
        if (realm.where(Products.class).findAll().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void saveTransaction(Transaction t) {
        realm = Realm.getInstance(realmConfiguration);
        realm.beginTransaction();
        Transaction transaction = realm.createObject(Transaction.class);
        transaction.setDate_hour(t.getDate_hour());
        transaction.setLast_digits(t.getLast_digits());
        transaction.setCard_holder_name(t.getCard_holder_name());
        transaction.setValue(t.getValue());
        realm.commitTransaction();
    }

    public RealmResults<Transaction> getTransactions() {
        realm = Realm.getInstance(realmConfiguration);
        return realm.where(Transaction.class).findAll();
    }

    public boolean isEmptyTransactions() {
        realm = Realm.getInstance(realmConfiguration);
        if (realm.where(Transaction.class).findAll().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
