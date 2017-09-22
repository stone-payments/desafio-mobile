package payments.stone.com.br.desafiomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;

import java.util.List;
import java.util.Random;

import payments.stone.com.br.desafiomobile.cart.CartActivity;
import payments.stone.com.br.desafiomobile.cart.CartAdapter;
import payments.stone.com.br.desafiomobile.cart.CartItem;
import payments.stone.com.br.desafiomobile.home.HomeActivity;

/**
 * Created by glarencezhao on 10/23/16.
 */

public class CreditCardActivity extends AppCompatActivity implements Navigation {

    public static final String KEY_FINISH_CHECKOUT_BUNDLE = "KEY_FINISH_CHECKOUT_BUNDLE";
    private final int CREATE_NEW_CARD = 0;

    private RecyclerView mCartRecyclerView;
    private CartAdapter mCartAdapter;
    private TextView mTotalPrice;

    private LinearLayout cardContainer;
    private Button addCardButton;
    private Button finishCheckoutButton;

    private List<CartItem> mCartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        Intent intent = getIntent();


        if (intent != null &&
                intent.getExtras() != null) {

            if (intent.getExtras().containsKey(KEY_FINISH_CHECKOUT_BUNDLE)) {
                mCartItems = intent.getExtras().getParcelableArrayList(KEY_FINISH_CHECKOUT_BUNDLE);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
        listeners();
    }

    private void initialize() {
        addCardButton = (Button) findViewById(R.id.add_card);
        cardContainer = (LinearLayout) findViewById(R.id.card_container);
        finishCheckoutButton = (Button) findViewById(R.id.finish_checkout);
        mCartRecyclerView = (RecyclerView) findViewById(R.id.checkout_cart_items);
        mTotalPrice = (TextView) findViewById(R.id.total_value);

        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populate();
    }

    private void populate() {
        if(new Random().nextBoolean()){
            CreditCardView sampleCreditCardView = new CreditCardView(this);

            String name = "Glarence Zhao";
            String cvv = "420";
            String expiry = "01/18";
            String cardNumber = "4242424242424242";

            sampleCreditCardView.setCVV(cvv);
            sampleCreditCardView.setCardHolderName(name);
            sampleCreditCardView.setCardExpiry(expiry);
            sampleCreditCardView.setCardNumber(cardNumber);

            cardContainer.addView(sampleCreditCardView);
            int index = cardContainer.getChildCount() - 1;
            addCardListener(index, sampleCreditCardView);
        }

        showCartDetails(mCartItems);

        addCardButton.setVisibility(cardContainer.getChildCount() > 0 ? View.GONE : View.VISIBLE);
        finishCheckoutButton.setVisibility(cardContainer.getChildCount() == 0 ? View.GONE : View.VISIBLE);

    }

    private void listeners() {
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
                Intent intent = new Intent(CreditCardActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void addCardListener(final int index, CreditCardView creditCardView) {
        creditCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreditCardView creditCardView = (CreditCardView) v;
                String cardNumber = creditCardView.getCardNumber();
                String expiry = creditCardView.getExpiry();
                String cardHolderName = creditCardView.getCardHolderName();
                String cvv = creditCardView.getCVV();

                Intent intent = new Intent(CreditCardActivity.this, CardEditActivity.class);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME, cardHolderName);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_NUMBER, cardNumber);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_EXPIRY, expiry);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_SHOW_CARD_SIDE, CreditCardUtils.CARD_SIDE_BACK);
                intent.putExtra(CreditCardUtils.EXTRA_VALIDATE_EXPIRY_DATE, false);

                // start at the CVV activity to edit it as it is not being passed
                intent.putExtra(CreditCardUtils.EXTRA_ENTRY_START_PAGE, CreditCardUtils.CARD_CVV_PAGE);
                startActivityForResult(intent, index);
            }
        });
    }

    public void showCartDetails(List<CartItem> items) {
        mCartAdapter = new CartAdapter(this, items, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mCartRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mCartRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        mCartRecyclerView.addItemDecoration(dividerItemDecoration);
        mCartRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCartRecyclerView.setAdapter(mCartAdapter);

        int totalAmount = 0;
        for (CartItem cartItem : mCartItems) {
            totalAmount += cartItem.getCount() * cartItem.getProduct().getPrice();
        }

        mTotalPrice.setText(Utils.getPriceFormatted(totalAmount));
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
//            Debug.printToast("Result Code is OK", getApplicationContext());

            String name = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            if (reqCode == CREATE_NEW_CARD) {

                CreditCardView creditCardView = new CreditCardView(this);

                creditCardView.setCVV(cvv);
                creditCardView.setCardHolderName(name);
                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);

                cardContainer.addView(creditCardView);
                int index = cardContainer.getChildCount() - 1;
                addCardListener(index, creditCardView);
                addCardButton.setVisibility(View.GONE);
                finishCheckoutButton.setVisibility(View.VISIBLE);

            } else {

                CreditCardView creditCardView = (CreditCardView) cardContainer.getChildAt(reqCode);

                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);
                creditCardView.setCardHolderName(name);
                creditCardView.setCVV(cvv);

            }
        }

    }

    @Override
    public void whenGoToDetails(Product product) {

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
        menu.findItem(R.id.action_show_cart).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);

        return true;
    }
}