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

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import BD.BD;
import BD.Transaction;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


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

    OkHttpClient client = new OkHttpClient();

    public void finishPurchase(View v){
        if(Cart.getInstance().getAllProducts().size() > 0) {
            Intent intent = new Intent(this, ConfirmTransaction.class);
            intent.putExtra("price", this.price);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Carrinho vazio.", Toast.LENGTH_SHORT).show();
        }
    }

}
