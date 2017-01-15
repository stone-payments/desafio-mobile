package com.am.store.starwars.integration.store.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Augusto on 13/01/2017.
 */

public class ProductVO {

    @JsonProperty("title")
    private String title;

    @JsonProperty("seller")
    private String seller;

    @JsonProperty("price")
    private String price;

    @JsonProperty("zipcode")
    private String zipCode;

    @JsonProperty("date")
    private String date;

    @JsonProperty("thumbnailHd")
    private String imageEndpoint;

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
}