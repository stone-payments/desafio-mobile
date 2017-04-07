package mobile.desafio.stone.desafiomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.button;

public class BuyActivity extends AppCompatActivity implements AsyncResponse {

    private RelativeLayout relativeLayout;
    private ArrayList<Product> allProducts;
    private ListView listView;
    private ProductListBaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        this.relativeLayout = (RelativeLayout)findViewById(R.id.buy_layout);
        this.allProducts = new ArrayList<Product>();
        this.listView = (ListView)findViewById(R.id.list);
        this.adapter = new ProductListBaseAdapter(this, this.allProducts);
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(onClickListener());

        GetJson getJson = new GetJson();
        getJson.delegate = this;
        getJson.execute("https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json");
    }
    @Override
    public void processFinish(String message){
        this.allProducts = new ArrayList<Product>();
        try {
            JSONArray jsonArray = new JSONArray(message);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                Product product = gson.fromJson(jsonObject.toString(), Product.class);
                this.allProducts.add(product);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.updateList();
    }

    private void updateList(){
        adapter.clear();
        adapter.addEntireData(this.allProducts);
        adapter.notifyDataSetChanged();
    }

    public AdapterView.OnItemClickListener onClickListener(){
        return(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = allProducts.get(position);
                Intent intent = new Intent(getBaseContext(), BuyItemActivity.class);
                intent.putExtra("title", product.getTitle());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });
    }

    public void confirmPurchase(View v){
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

}

