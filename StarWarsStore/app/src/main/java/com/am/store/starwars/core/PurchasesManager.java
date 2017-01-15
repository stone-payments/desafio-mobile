package com.am.store.starwars.core;

import com.am.store.starwars.dao.PurchaseDAO;
import com.am.store.starwars.dao.ShoppingCartDAO;
import com.am.store.starwars.exception.StarWarServiceException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.model.store.product.Product;
import com.am.store.starwars.model.store.product.Purchase;

import java.util.List;

/**
 * Created by Augusto on 15/01/2017.
 */

public class PurchasesManager {

    private static final String LOG_CONSTANT = PurchasesManager.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    private PurchaseDAO purchaseDAO;

    public PurchasesManager() {
        this.purchaseDAO = new PurchaseDAO();
    }

    public List<Purchase> puchases() throws StarWarServiceException {

        try {
            List<Purchase> purchases = this.purchaseDAO.getPurchases();
            return purchases;
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to read item in ShoppingCart", e);
            throw new StarWarServiceException(e);
        }
    }
}