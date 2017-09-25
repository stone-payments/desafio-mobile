package payments.stone.com.br.desafiomobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by william.gouvea on 9/19/17.
 */

public class Product implements Parcelable {
    private String title;

    private long price;

    @SerializedName("zipcode")
    private String zipCode;

    private String seller;

    private String thumbnailHd;

    private String date;


    public String getTitle() {
        return title;
    }

    public Product title(String title) {
        this.title = title;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public String getPriceFormatted() {
        return "R$ " + String.format("%.2f", price / 1000.0);

    }

    public Product price(long price) {
        this.price = price;
        return this;
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

    public Product seller(String seller) {
        this.seller = seller;
        return this;
    }

    public String getThumbnailHd() {
        return thumbnailHd;
    }

    public Product thumb(String thumbnailHd) {
        this.thumbnailHd = thumbnailHd;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeLong(this.price);
        dest.writeString(this.zipCode);
        dest.writeString(this.seller);
        dest.writeString(this.thumbnailHd);
        dest.writeString(this.date);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.title = in.readString();
        this.price = in.readLong();
        this.zipCode = in.readString();
        this.seller = in.readString();
        this.thumbnailHd = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
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
