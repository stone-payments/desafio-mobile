package com.am.store.starwars.dao;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.am.store.starwars.model.store.product.Product;
import com.am.store.starwars.model.store.product.Purchase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Augusto on 16/01/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ShoppingCartDAOTest {

    private static final String LOG_CONSTANT = "ShoppingCartDAOTest";

    @Test
    public void insertProductTest() throws Exception {
        try {
            ShoppingCartDAO dao = new ShoppingCartDAO();
            dao.insertProduct(newProduct());
            assertTrue(true);
        } catch (Exception e) {
            Log.e(LOG_CONSTANT, "Error", e);
            assertTrue(false);
        }
    }

    @Test
    public void getShoppingCartTest() throws Exception {
        try {
            ShoppingCartDAO dao = new ShoppingCartDAO();
            dao.insertProduct(newProduct());

            List<Product> products = dao.getShoppingCart();
            if (products != null && (!products.isEmpty())) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        } catch (Exception e) {
            Log.e(LOG_CONSTANT, "Error", e);
            assertTrue(false);
        }
    }

    @Test
    public void isShoppingCartEmptyTest() throws Exception {
        try {
            ShoppingCartDAO dao = new ShoppingCartDAO();
            dao.clearShoppingCart();

            List<Product> products = dao.getShoppingCart();
            if (products == null || products.isEmpty()) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        } catch (Exception e) {
            Log.e(LOG_CONSTANT, "Error", e);
            assertTrue(false);
        }
    }

    @Test
    public void deleteProductTest() throws Exception {
        try {
            ShoppingCartDAO dao = new ShoppingCartDAO();
            dao.insertProduct(newProduct());

            List<Product> products = dao.getShoppingCart();
            dao.deleteProduct(products.get(0));

            assertTrue(true);
        } catch (Exception e) {
            Log.e(LOG_CONSTANT, "Error", e);
        }
    }

    private Product newProduct() {
        Product product = new Product();
        product.setDate("2000021");
        product.setId("IdKey");
        product.setPrice("1000");
        product.setImageEndpoint("http://url.qualquer.com");
        product.setSeller("PontoFrio");
        product.setTitle("Tenis");
        product.setZipCode("20000-000");

        return product;
    }
}