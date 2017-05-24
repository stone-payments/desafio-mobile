package com.stone.lfernandosantos.storewars.models;

import java.io.Serializable;

/**
 * Created by lf.fernandodossantos on 20/05/17.
 */

public class Product implements Serializable{
    public Long id;
    public String title;
    public Double price;
    public String zipcode;
    public String seller;
    public String thumbnailHd;
    public String date;
    public String carrinho;
    public String compra;

    public String getPrice() {

        Double p = price / 100;

        String priceFormat = String.format("%.2f",p);
        return priceFormat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", zipcode='" + zipcode + '\'' +
                ", seller='" + seller + '\'' +
                ", thumbnailHd='" + thumbnailHd + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
