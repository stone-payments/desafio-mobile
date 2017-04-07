package mobile.desafio.stone.desafiomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gerson on 05/04/2017.
 */

public class BuyItemActivity extends AppCompatActivity {

    private Product product;
    private boolean alreadyOnCart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyitem);

        Intent intent = getIntent();
        this.product = (Product)intent.getSerializableExtra("product");

        TextView itemName = (TextView)findViewById(R.id.itemName);
        itemName.setText(this.product.getTitle());

        TextView itemPrice = (TextView)findViewById(R.id.itemPrice);
        itemPrice.setText("R$ " + this.product.getPrice());

        Button button = (Button)findViewById(R.id.btn_confirmProductBuy);
        if(Cart.getInstance().containsProduct(this.product)){
            this.alreadyOnCart = true;
            button.setText("Já adicionado no carrinho!");
        }
    }

    public void buyItem(View v){
        if(this.alreadyOnCart == false) {
            Cart.getInstance().addProduct(product);
            Toast.makeText(this, product.getTitle() + " adicionado ao carrinho", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
