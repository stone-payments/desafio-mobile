package com.am.store.starwars.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.am.store.starwars.model.store.product.Purchase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Augusto on 16/01/2017.
 */

@RunWith(AndroidJUnit4.class)
public class PurchaseDAOTest {

    private static final String LOG_CONSTANT = "PurchaseDAOTest";

    @Test
    public void insertPurchase() throws Exception {
        try {
            PurchaseDAO dao = new PurchaseDAO();
            dao.insertPurchase(newPurchase());
            assertTrue(true);
        }catch (Exception e) {
            Log.e(LOG_CONSTANT, "Error", e);
            assertTrue(false);
        }
    }

    @Test
    public void getAllPurchases() throws Exception {
        try {
            PurchaseDAO dao = new PurchaseDAO();
            dao.insertPurchase(newPurchase());

            List<Purchase> purchases = dao.getPurchases();
            if(purchases != null && (!purchases.isEmpty())) {
                assertTrue(true);
            }
            else {
                assertTrue(false);
            }
        }catch (Exception e) {
            Log.e(LOG_CONSTANT, "Error", e);
            assertTrue(false);
        }
    }

    private Purchase newPurchase() {
        Purchase purchase = new Purchase();
        purchase.setLastDigitsCardNumber("4444");
        purchase.setCardHolder("Augusto Marinho");
        purchase.setAmount(1000);
        purchase.setDateTime(System.currentTimeMillis());

        return purchase;
    }
}
