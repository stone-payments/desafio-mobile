package com.am.store.starwars.model.store.product;

import com.am.store.starwars.integration.store.vo.ProductVO;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Augusto on 14/01/2017.
 */

public class Product extends RealmObject {

    @PrimaryKey
    private String id;

    private String title;

    private String seller;

    private String price;

    private String zipCode;

    private String date;

    private String imageEndpoint;

    public Product() {

    }

    public Product(ProductVO product) {
        this.setId(UUID.randomUUID().toString());
        this.setSeller(product.getSeller());
        this.setDate(product.getDate());
        this.setImageEndpoint(product.getImageEndpoint());
        this.setPrice(product.getPrice());
        this.setTitle(product.getTitle());
        this.setZipCode(product.getZipCode());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageEndpoint() {
        return imageEndpoint;
    }

    public void setImageEndpoint(String imageEndpoint) {
        this.imageEndpoint = imageEndpoint;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
