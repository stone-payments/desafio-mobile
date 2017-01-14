package com.am.store.starwars.dao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.am.store.starwars.exception.StarWarPersistenceException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.model.store.product.ProductEntity;
import com.am.store.starwars.model.store.product.ProductImageEntity;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Augusto on 14/01/2017.
 */

public class ProductImageDAO {

    private static final String LOG_CONSTANT = ShoppingCartDAO.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    public ProductImageDAO() {

    }

    public void insertImage(String id, Bitmap bitmap) throws StarWarPersistenceException {

        Realm realm = Realm.getDefaultInstance();
        try {
            ProductImageEntity entity = new ProductImageEntity();
            entity.setId(id);
            entity.setBitmap(bitmap);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(entity);
            realm.commitTransaction();
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to insert Product Image!", e);
            realm.cancelTransaction();
            throw new StarWarPersistenceException(e);
        } finally {
            //realm.close();
        }
    }

    public Bitmap getImage(final String imageId) throws StarWarPersistenceException {

        Realm realm = Realm.getDefaultInstance();

        try {
            Bitmap bitmap = null;
            RealmResults<ProductImageEntity> result = realm.where(ProductImageEntity.class).equalTo("id", imageId).findAll();

            if (!result.isEmpty()) {
                ProductImageEntity entity = result.get(0);

                Bitmap decodedByte = BitmapFactory.decodeByteArray(entity.getBitmap(), 0, entity.getBitmap().length);
                bitmap = decodedByte;
            }

            return bitmap;
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to get Image bitmap", e);
            throw new StarWarPersistenceException(e);
        } finally {
            //realm.close();
        }
    }
}
