package com.luiztorres.stone.starwarsshop.extras;

/**
 * Created by Dindal on 30/12/2016.
 */

public class Item {
    private String Name, Seller, ImageURL;
    private double Price;

    private int count = 1;

    public Item(String name, String seller, String imageURL, double price) {
        Name = name;
        Seller = seller;
        ImageURL = imageURL;
        Price = price;
    }

    public String getSeller() {
        return Seller;
    }

    public String getName() {
        return Name;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public double getPrice() {
        return Price;
    }

    public int getCount() {return count; }

    public void addCount(int count) {this.count += count;}

    public void removeCount(int count) {this.count -= count;}
}
