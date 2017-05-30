package com.hernand.starwars.vo;

import java.io.Serializable;

/**
 * Created by Nando on 27/05/2017.
 */

public class ProductVO implements Serializable {

    public String title;
    public Long price;
    public String zipcode;
    public String seller;
    public String thumbnailHd;
    public String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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

    @Override
    public String toString() {
        return "ProductVO{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", zipcode='" + zipcode + '\'' +
                ", seller='" + seller + '\'' +
                ", thumbnailHd='" + thumbnailHd + '\'' +
                ", date='" + date + '\'' +
                '}';
    }


}
