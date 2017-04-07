package com.am.store.starwars.dao;

import com.am.store.starwars.exception.StarWarPersistenceException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.model.store.product.Product;
import com.am.store.starwars.model.store.product.Purchase;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Augusto on 15/01/2017.
 */

public class PurchaseDAO {

    private static final String LOG_CONSTANT = PurchaseDAO.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    public PurchaseDAO() {

    }

    public void insertPurchase(Purchase purchase) throws StarWarPersistenceException {

        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();
            purchase = realm.copyToRealm(purchase);
            realm.commitTransaction();
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to insert purchase", e);
            realm.cancelTransaction();
            throw new StarWarPersistenceException("Problems to insert purchase", e);
        } finally {
            //realm.close();
        }
    }

    public List<Purchase> getPurchases() throws StarWarPersistenceException {

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmQuery<Purchase> query = realm.where(Purchase.class);
            RealmResults<Purchase> purchases = query.findAllSorted("dateTime", Sort.DESCENDING);
            return purchases;
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to read all purchaes!", e);
            throw new StarWarPersistenceException(e);
        } finally {
            //realm.close();
        }
    }
}