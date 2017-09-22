package payments.stone.com.br.desafiomobile;

/**
 * Created by william.gouvea on 9/21/17.
 */

class CartItem {
    private Product product;
    private int count;

    public CartItem(Product product) {
        this.product = product;
    }

    public CartItem increment(int amount){
        count+=amount;
        return this;
    }

    public String getTotalPriceByCount(){
        return "R$ " + String.format("%.2f",(product.getPrice() * count)/1000.0);
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }
}
