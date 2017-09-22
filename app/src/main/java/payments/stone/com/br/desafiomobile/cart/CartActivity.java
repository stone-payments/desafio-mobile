package payments.stone.com.br.desafiomobile.cart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import payments.stone.com.br.desafiomobile.BaseActivity;
import payments.stone.com.br.desafiomobile.CreditCardActivity;
import payments.stone.com.br.desafiomobile.R;
import payments.stone.com.br.desafiomobile.Utils;

import static payments.stone.com.br.desafiomobile.CreditCardActivity.KEY_FINISH_CHECKOUT_BUNDLE;

public class CartActivity extends BaseActivity implements CartView {
    private RecyclerView mCartRecyclerView;
    private CartAdapter mCartAdapter;
    private TextView mTotalPrice;
    private Button mCheckoutButton;
    private ProgressBar mCartProgressBar;
    private long totalAmount;

    private CartPresenter mPresenter;

    private List<CartItem> mCartItems = new ArrayList<>();
    private LinearLayout mContentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        loadViews();
        loadListeners();

        mPresenter = new CartPresenter(this).loadCart();
    }


    private void loadListeners() {
        mCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int GET_NEW_CARD = 2;

                Intent intent = new Intent(CartActivity.this, CreditCardActivity.class);
                intent.putParcelableArrayListExtra(KEY_FINISH_CHECKOUT_BUNDLE, (ArrayList<? extends Parcelable>) mCartItems);
                startActivityForResult(intent, GET_NEW_CARD);
            }
        });
    }

    @Override
    public void loadViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCartRecyclerView = (RecyclerView) findViewById(R.id.cart_items);
        mTotalPrice = (TextView) findViewById(R.id.total_value);
        mCheckoutButton = (Button) findViewById(R.id.checkout_button);
        mCartProgressBar = (ProgressBar) findViewById(R.id.activity_cart_progress);
        mContentContainer = (LinearLayout) findViewById(R.id.content_container);
    }

    @Override
    public void showTotalPrice(List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            totalAmount += cartItem.getCount() * cartItem.getProduct().getPrice();
        }
        mTotalPrice.setText(Utils.getPriceFormatted(totalAmount));
    }

    @Override
    public void showCartItems(List<CartItem> items) {
        mContentContainer.setVisibility(View.VISIBLE);
        mCartItems = items;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mCartRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mCartRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        mCartRecyclerView.addItemDecoration(dividerItemDecoration);
        mCartRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mCartAdapter = new CartAdapter(this, mCartItems, this);
        mCartRecyclerView.setAdapter(mCartAdapter);
    }

    @Override
    public void showLoading() {
        mCartProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mCartProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void hideError() {

    }
}
