package mobile.desafio.stone.desafiomobile;

import java.io.Serializable;

/**
 * Created by Gerson on 05/04/2017.
 */

public class Product implements Serializable {
    private String title;
    private int price;
    private String zipCode;
    private String seller;
    private String thumbnailHd; //Depois vira Imagem
    private String date;

    public Product(String title, int price, String zipCode, String seller, String thumbnailHd, String date){
        this.title = title;
        this.price = price;
        this.zipCode = zipCode;
        this.seller = seller;
        this.thumbnailHd = thumbnailHd;
        this.date = date;
    }

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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
