package com.stone.mobile.stonestore.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Produto {

    private long id;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("seller")
    @Expose
    private String seller;
    @SerializedName("thumbnailHd")
    @Expose
    private String thumbnailHd;
    @SerializedName("date")
    @Expose
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getThumbnailHd() {
        return thumbnailHd;
    }

    public void setThumbnailHd(String thumbnailHd) {
        this.thumbnailHd = thumbnailHd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
