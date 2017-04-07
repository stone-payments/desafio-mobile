package mobile.desafio.stone.desafiomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import BD.BD;
import BD.Transaction;


public class CartActivity extends AppCompatActivity  implements SpinnerListener{

    private ListView listView;
    private CartProductListBaseAdapter adapter;
    private TextView totalPrice;
    private int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listView = (ListView)findViewById(R.id.cart_list);
        this.adapter = new CartProductListBaseAdapter(this, Cart.getInstance().getAllProducts(), this);
        this.listView.setAdapter(this.adapter);

        this.totalPrice = (TextView)findViewById(R.id.totalPrice);
        this.updateTotalPrice();
    }

    private void updateTotalPrice(){
        price = 0;
        ArrayList<CartProduct> cartProducts = Cart.getInstance().getAllProducts();
        for(int i = 0; i < cartProducts.size(); i++){
            price += cartProducts.get(i).getPrice() * cartProducts.get(i).getAmount();
        }

        this.totalPrice.setText("R$ " + price);
    }



    @Override
    public void onSpinnerItemSelected(int spinnerPosition, int optionPosition){
        int amountOfProduct = optionPosition + 1;
        Cart.getInstance().getAllProducts().get(spinnerPosition).setAmount(amountOfProduct);
        updateTotalPrice();
    }

    public void finishPurchase(View v){
        if(Cart.getInstance().getAllProducts().size() > 0) {
            BD bd = new BD(this);
            bd.insert(new Transaction(this.price, "Luke Skywalker", "1234"));

            Toast.makeText(this, "Compra realizada com sucesso!", Toast.LENGTH_SHORT).show();
            Cart.getInstance().clean();
            finish();
        }
        else{
            Toast.makeText(this, "Carrinho vazio.", Toast.LENGTH_SHORT).show();
        }
    }

}
