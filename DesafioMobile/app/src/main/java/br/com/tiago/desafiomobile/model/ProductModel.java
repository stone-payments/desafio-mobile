package br.com.tiago.desafiomobile.model;

import android.graphics.Bitmap;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

public class ProductModel {

    private String title;
    private Double price;
    private String zipcode;
    private String seller;
    private String thumbnailHd;
    private String date;
    private Bitmap bitmap;

    @Override
    public String toString() {
        return "ProductModel{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", zipcode='" + zipcode + '\'' +
                ", seller='" + seller + '\'' +
                ", thumbnailHd='" + thumbnailHd + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
