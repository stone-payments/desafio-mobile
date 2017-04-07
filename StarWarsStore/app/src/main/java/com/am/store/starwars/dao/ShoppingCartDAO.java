package com.am.store.starwars.dao;

import com.am.store.starwars.exception.StarWarPersistenceException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.model.store.product.Product;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Augusto on 14/01/2017.
 */

public class ShoppingCartDAO {

    private static final String LOG_CONSTANT = ShoppingCartDAO.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    public ShoppingCartDAO() {
    }

    public void insertProduct(Product product) throws StarWarPersistenceException {

        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();
            product = realm.copyToRealm(product);
            realm.commitTransaction();
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to insert product", e);
            realm.cancelTransaction();
            throw new StarWarPersistenceException("Problems to insert product", e);
        } finally {
            //realm.close();
        }
    }

    public List<Product> getShoppingCart() throws StarWarPersistenceException {

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmQuery<Product> query = realm.where(Product.class);
            RealmResults<Product> productsCart = query.findAll();
            return productsCart;
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to read all items in cart", e);
            throw new StarWarPersistenceException(e);
        } finally {
            //realm.close();
        }
    }

    public void deleteProduct(final Product product) throws StarWarPersistenceException {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Product> result = realm.where(Product.class).equalTo("id", product.getId()).findAll();
                    result.deleteAllFromRealm();
                }
            });
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to delete product");
            throw new StarWarPersistenceException(e);
        }
        finally {
            //realm.close();
        }
    }

    public void clearShoppingCart() throws StarWarPersistenceException {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmQuery<Product> query = realm.where(Product.class);
                    RealmResults<Product> productsCart = query.findAll();
                    productsCart.deleteAllFromRealm();
                }
            });
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to delete all items in cart", e);
            throw new StarWarPersistenceException(e);
        }
        finally {
            //realm.close();
        }
    }
}