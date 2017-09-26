package payments.stone.com.br.desafiomobile.checkout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;

import java.util.List;
import java.util.Random;

import payments.stone.com.br.desafiomobile.ShopitApplication;
import payments.stone.com.br.desafiomobile.commons.Navigation;
import payments.stone.com.br.desafiomobile.model.Order;
import payments.stone.com.br.desafiomobile.views.BaseActivity;
import payments.stone.com.br.desafiomobile.R;
import payments.stone.com.br.desafiomobile.model.CartItem;

/**
 * Created by glarencezhao on 10/23/16.
 */

public class CreditCardActivity extends BaseActivity implements payments.stone.com.br.desafiomobile.checkout.CreditCardView {

    public static final String KEY_FINISH_CHECKOUT_BUNDLE = "KEY_FINISH_CHECKOUT_BUNDLE";
    private final int CREATE_NEW_CARD = 0;

    private RecyclerView mCartRecyclerView;
    private CartAdapter mCartAdapter;
    private TextView mTotalPrice;

    private LinearLayout cardContainer;
    private Button addCardButton;
    private Button finishCheckoutButton;

    private List<CartItem> mCartItems;

    private CreditCardPresenter mPresenter;
    private CreditCardView mCreditCardView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        loadViews();
        loadListeners();

        mPresenter = new CreditCardPresenter(this)
                .loadCart();
    }

    @Override
    public List<CartItem> handleIntent() {
        Intent intent = getIntent();

        if (intent != null &&
                intent.getExtras() != null) {

            if (intent.getExtras().containsKey(KEY_FINISH_CHECKOUT_BUNDLE)) {
                mCartItems = intent.getExtras().getParcelableArrayList(KEY_FINISH_CHECKOUT_BUNDLE);
            }
        }

        return mCartItems;
    }

    @Override
    public void loadViews() {
        addCardButton = (Button) findViewById(R.id.add_card);
        cardContainer = (LinearLayout) findViewById(R.id.card_container);
        finishCheckoutButton = (Button) findViewById(R.id.finish_checkout);
        mCartRecyclerView = (RecyclerView) findViewById(R.id.checkout_cart_items);
        mTotalPrice = (TextView) findViewById(R.id.total_value);

        loadToolbar();
        populate();
    }

    private void loadToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Context context() {
        return this;
    }

    private void populate() {
        if (new Random().nextBoolean()) {
            mCreditCardView = new CreditCardView(this);

            String name = "Glarence Zhao";
            String cvv = "420";
            String expiry = "01/18";
            String cardNumber = "4242424242424242";

            mCreditCardView.setCVV(cvv);
            mCreditCardView.setCardHolderName(name);
            mCreditCardView.setCardExpiry(expiry);
            mCreditCardView.setCardNumber(cardNumber);

            cardContainer.addView(mCreditCardView);
        }


        addCardButton.setVisibility(cardContainer.getChildCount() > 0 ? View.GONE : View.VISIBLE);
        finishCheckoutButton.setVisibility(cardContainer.getChildCount() == 0 ? View.GONE : View.VISIBLE);

    }

    public void loadListeners() {
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreditCardActivity.this, CardEditActivity.class);
                startActivityForResult(intent, CREATE_NEW_CARD);
            }
        });

        finishCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mPresenter.checkout();


            }
        });
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
//            Debug.printToast("Result Code is OK", getApplicationContext());

            String name = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            if (reqCode == CREATE_NEW_CARD) {

                mCreditCardView = new CreditCardView(this);

                mCreditCardView.setCVV(cvv);
                mCreditCardView.setCardHolderName(name);
                mCreditCardView.setCardExpiry(expiry);
                mCreditCardView.setCardNumber(cardNumber);

                cardContainer.addView(mCreditCardView);

                addCardButton.setVisibility(View.GONE);
                finishCheckoutButton.setVisibility(View.VISIBLE);

            }
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_show_cart).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_reset_cart).setVisible(true);
        return true;
    }

    @Override
    public void showTotalPrice(String price) {
        mTotalPrice.setText(price);
    }

    @Override
    public void showCartItems(List<CartItem> items) {
        mCartItems = items;
        mCartAdapter = new CartAdapter(this, items, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mCartRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mCartRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        mCartRecyclerView.addItemDecoration(dividerItemDecoration);
        mCartRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCartRecyclerView.setAdapter(mCartAdapter);
    }


    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(CreditCardActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// Setting Message
            mProgressDialog.setMessage("Processing Order...");
            mProgressDialog.setTitle("Checkout");
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if(mProgressDialog!=null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void hideError() {

    }

    @Override
    public Order filledOrder() {
        return new Order()
                .cardNumber(mCreditCardView.getCardNumber())
                .cardHolder(mCreditCardView.getCardHolderName())
                .cvv(mCreditCardView.getCVV())
                .expDate(mCreditCardView.getExpiry())
                .value(Long.toString(ShopitApplication.getInstance().provideCart().getTotalAmount()));
    }

    @Override
    public Navigation navigation() {
        return this;
    }
}