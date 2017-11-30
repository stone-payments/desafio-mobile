package kelly.com.desafiostone.models;

import android.graphics.Bitmap;

/**
 * Created by kelly on 29/11/17.
 */

public class Item {

    private String title;
    private double price;
    private String seller;
    private String imageURL;
    private Bitmap imageBitmap;

    public Item() {}

    public Item(String title, double price, String seller, String image) {
        this.title = title;
        this.price = price;
        this.seller = seller;
        this.imageURL = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String image) {
        this.imageURL = image;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}
