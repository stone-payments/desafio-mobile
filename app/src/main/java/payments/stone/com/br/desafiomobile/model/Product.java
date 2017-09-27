package payments.stone.com.br.desafiomobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by william.gouvea on 9/19/17.
 */

public class Product  extends RealmObject implements Parcelable {
    private static int objId = 0;

    @PrimaryKey
    private int id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (price != product.price) return false;
        if (title != null ? !title.equals(product.title) : product.title != null) return false;
        if (zipCode != null ? !zipCode.equals(product.zipCode) : product.zipCode != null)
            return false;
        if (seller != null ? !seller.equals(product.seller) : product.seller != null) return false;
        if (thumbnailHd != null ? !thumbnailHd.equals(product.thumbnailHd) : product.thumbnailHd != null)
            return false;
        return date != null ? date.equals(product.date) : product.date == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (int) (price ^ (price >>> 32));
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (seller != null ? seller.hashCode() : 0);
        result = 31 * result + (thumbnailHd != null ? thumbnailHd.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
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
        id = objId++;
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
