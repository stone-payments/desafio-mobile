package victorcruz.dms.produto;

import android.graphics.Bitmap;

public class Product {
    private String title;
    private int price;
    private String zipcode;
    private String seller;
    private Bitmap image;
    private String date;

    public Product(String title, int price, String zipcode, String seller, Bitmap image, String date) {
        this.title = title;
        this.price = price;
        this.zipcode = zipcode;
        this.seller = seller;
        this.image = image;
        this.date = date;
    }

    public Product(Product product) {
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.zipcode = product.getZipcode();
        this.seller = product.getSeller();
        this.image = product.getImage();
        this.date = product.getDate();
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getZipcode(){
        return zipcode;
    }

    public String getSeller(){
        return seller;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
