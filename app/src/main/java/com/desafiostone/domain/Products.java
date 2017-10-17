package com.desafiostone.domain;

/**
 * Created by Filipi Andrade on 17-Oct-17.
 */

public class Products {

    private String title;
    private String price;
    private String zipcode;
    private String seller;
    private String thumbnailHd;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
}
