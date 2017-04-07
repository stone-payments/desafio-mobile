package mobile.desafio.stone.desafiomobile;

import java.util.ArrayList;

/**
 * Created by Gerson on 06/04/2017.
 */

public class Cart {

    private static Cart cart;
    private ArrayList<CartProduct> cartProducts;

    private Cart(){
        this.cartProducts = new ArrayList<CartProduct>();
    }

    public static  Cart getInstance(){
        if(cart == null)
            cart = new Cart();

        return cart;
    }

    public ArrayList<CartProduct> getAllProducts(){
        return this.cartProducts;
    }

    public void addProduct(Product p){
        if(containsProduct(p) == false){
            CartProduct cartProduct = new CartProduct(p);
            this.cartProducts.add(cartProduct);
        }
    }

    public boolean containsProduct(Product p){
        for(int i = 0; i < this.cartProducts.size(); i++){
            if(this.cartProducts.get(i).getTitle().equals(p.getTitle())){
                return true;
            }
        }

        return false;
    }

    public void clean(){
        this.cartProducts = new ArrayList<CartProduct>();
    }
}
