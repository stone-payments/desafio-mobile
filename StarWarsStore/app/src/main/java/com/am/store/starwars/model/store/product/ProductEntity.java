package com.am.store.starwars.model.store.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Augusto on 14/01/2017.
 */

public class ProductEntity extends RealmObject {

    @PrimaryKey
    private String id;

    private String title;

    private String seller;

    private String price;

    private String zipCode;

    private String date;

    private String imageEndpoint;

    public ProductEntity() {

    }

    public ProductEntity(Product product) {
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
