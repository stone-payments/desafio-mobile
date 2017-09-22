package payments.stone.com.br.desafiomobile.cart;


import android.os.Parcel;
import android.os.Parcelable;

import payments.stone.com.br.desafiomobile.Product;

/**
 * Created by william.gouvea on 9/21/17.
 */

public class CartItem implements Parcelable {
    private Product product;
    private int count;

    public CartItem(Product product) {
        this.product = product;
    }

    public CartItem increment(int amount) {
        count += amount;
        return this;
    }

    public String getTotalPriceByCount() {
        return "R$ " + String.format("%.2f", (product.getPrice() * count) / 1000.0);
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.product, flags);
        dest.writeInt(this.count);
    }

    protected CartItem(Parcel in) {
        this.product = in.readParcelable(Product.class.getClassLoader());
        this.count = in.readInt();
    }

    public static final Parcelable.Creator<CartItem> CREATOR = new Parcelable.Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel source) {
            return new CartItem(source);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };
}
