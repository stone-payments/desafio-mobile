package payments.stone.com.br.desafiomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements Navigation {
    private RecyclerView mCartRecyclerView;
    private CartAdapter mCartAdapter;
    private TextView mTotalPrice;
    private long totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCartRecyclerView = (RecyclerView) findViewById(R.id.cart_items);
        mTotalPrice = (TextView) findViewById(R.id.total_value);

        List<CartItem> items = new ArrayList<>();
        items.add(
                new CartItem(
                        new Product()
                                .title("Yoda Poster")
                                .seller("Lucas Arts")
                                .price(90000)).increment(2));

        items.add(
                new CartItem(
                        new Product()
                                .title("Camisa StormTrooper")
                                .seller("Lucas Arts")
                                .thumb("http://mlb-s1-p.mlstatic.com/moletom-star-wars-stormtrooper-12754-MLB20066273702_032014-F.jpg")
                                .price(725000))
                                .increment(1));

        showCartItems(items);

        for (CartItem cartItem : items) {
            totalAmount += cartItem.getCount() * cartItem.getProduct().getPrice();
        }

        mTotalPrice.setText(Utils.getPriceFormatted(totalAmount));

    }

    public void showCartItems(List<CartItem> items) {
        mCartAdapter = new CartAdapter(this, items, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mCartRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mCartRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        mCartRecyclerView.addItemDecoration(dividerItemDecoration);
        mCartRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCartRecyclerView.setAdapter(mCartAdapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_cart:
                startActivity(new Intent(this, CartActivity.class));
                return true;


            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem search = menu.findItem(R.id.action_search);
        if (search != null) {
            search.setVisible(false);
        }

        MenuItem cart = menu.findItem(R.id.action_show_cart);
        if (cart != null) {
            cart.setVisible(false);
        }

        return true;
    }

    @Override
    public void whenGoToDetails(Product product) {

    }
}
