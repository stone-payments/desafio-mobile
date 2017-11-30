package kelly.com.desafiostone.models;

/**
 * Created by kelly on 29/11/17.
 */

public class Item {

    private String title;
    private double price;
    private String seller;
    private String image;

    public Item() {}

    public Item(String title, double price, String seller, String image) {
        this.title = title;
        this.price = price;
        this.seller = seller;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
