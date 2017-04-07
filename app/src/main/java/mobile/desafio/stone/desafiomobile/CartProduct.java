package mobile.desafio.stone.desafiomobile;

/**
 * Created by Gerson on 06/04/2017.
 */

public class CartProduct extends Product {

    private int amount;

    public CartProduct(String title, int price, String zipCode, String seller, String thumbnailHd, String date) {
        super(title, price, zipCode, seller, thumbnailHd, date);
        this.amount = 1;
    }

    public CartProduct(Product p){
        super(p.getTitle(), p.getPrice(), p.getZipCode(), p.getSeller(), p.getThumbnailHd(), p.getDate());
        this.amount = 1;
    }

    public int getAmount(){
        return this.amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

}
