package payments.stone.com.br.desafiomobile.checkout;

import java.util.ArrayList;
import java.util.List;

import payments.stone.com.br.desafiomobile.commons.Utils;
import payments.stone.com.br.desafiomobile.model.CartItem;
import payments.stone.com.br.desafiomobile.model.Product;

/**
 * Created by william.gouvea on 9/25/17.
 */

public class Cart {
    private List<CartItem> items;
    private long totalAmount = 0;

    public Cart() {
        items = new ArrayList<>();
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public String total() {
        for (CartItem cartItem : items) {
            totalAmount += cartItem.getCount() * cartItem.getProduct().getPrice();
        }

        return Utils.getPriceFormatted(totalAmount);
    }

    public Cart addItem(Product product) {
        for(CartItem item :items){
            if(item.getProduct().equals(product)){
                item.increment(1);
                return this;
            }
        }

        items.add(new CartItem(product).increment(1));
        return this;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Cart reset() {
        items = new ArrayList<>();
        totalAmount = 0;
        return this;
    }
}
