package com.jademcosta.starstore.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    private String title;
    private int price;
    private String zipcode;
    private String seller;
    private String thumbnailHd;
    private String date;

    public Item() {

    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    protected Item(Parcel in) {
        title = in.readString();
        price = in.readInt();
        zipcode = in.readString();
        seller = in.readString();
        thumbnailHd = in.readString();
        date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(price);
        dest.writeString(zipcode);
        dest.writeString(seller);
        dest.writeString(thumbnailHd);
        dest.writeString(date);
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
