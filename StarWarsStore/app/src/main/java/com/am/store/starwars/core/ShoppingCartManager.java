package com.am.store.starwars.core;

import com.am.store.starwars.dao.ShoppingCartDAO;
import com.am.store.starwars.exception.StarWarServiceException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.model.store.product.Product;
import com.am.store.starwars.model.store.product.ProductEntity;
import com.am.store.starwars.view.adapter.ProductViewAdapter;

import java.util.List;

/**
 * Created by Augusto on 14/01/2017.
 */

public class ShoppingCartManager {

    private static final String LOG_CONSTANT = ShoppingCartManager.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    private ShoppingCartDAO cartDao;

    public ShoppingCartManager() {
        this.cartDao = new ShoppingCartDAO();
    }

    public void addItem(ProductEntity product) throws StarWarServiceException {
        try {
            this.cartDao.insertProduct(product);
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to insert item in Shopping Cart", e);
            throw new StarWarServiceException(e);
        }
    }

    public List<ProductEntity> getShoppingCart() throws StarWarServiceException {
        try {
            List<ProductEntity> products = this.cartDao.getShoppingCart();

            return products;
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to read item in ShoppingCart", e);
            throw new StarWarServiceException(e);
        }
    }

    public void deleteProduct(ProductEntity product) throws StarWarServiceException {
        try {
            this.cartDao.deleteProduct(product);
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to delete product");
            throw new StarWarServiceException(e);
        }
    }

    public void clearShoppingCart() throws StarWarServiceException {
        try {
            this.cartDao.clearShoppingCart();
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to delete ShoppingCart");
            throw new StarWarServiceException(e);
        }
    }
}
