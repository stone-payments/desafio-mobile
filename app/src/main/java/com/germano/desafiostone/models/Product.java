package com.germano.desafiostone.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by germano on 24/08/17.
 */

public class Product implements Parcelable{

    private String title;

    private int price;

    private String zipcode;

    private String seller;

    @SerializedName("thumbnailHd")
    private String urlImage;

    private String date;

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getSeller() {
        return seller;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getDate() {
        return date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.price);
        dest.writeString(this.zipcode);
        dest.writeString(this.seller);
        dest.writeString(this.urlImage);
        dest.writeString(this.date);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.title = in.readString();
        this.price = in.readInt();
        this.zipcode = in.readString();
        this.seller = in.readString();
        this.urlImage = in.readString();
        this.date = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
