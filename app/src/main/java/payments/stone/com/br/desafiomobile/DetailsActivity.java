package payments.stone.com.br.desafiomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import payments.stone.com.br.desafiomobile.cart.CartActivity;

public class DetailsActivity extends AppCompatActivity implements DetailsView {

    public static final String KEY_DETAILS_PRODUCT_BUNDLE = "DETAILS_PRODUCT_BUNDLE";
    private Product mProduct;

    private TextView mTitle;
    private TextView mSeller;
    private TextView mPrice;
    private TextView mZipCode;
    private TextView mDate;

    ImageView mExpandedImage;


    private DetailsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        handleIntent();
        loadViews();
        mPresenter = new DetailsPresenter(this).loadProduct(mProduct);

    }

    private void handleIntent() {
        Intent intent = getIntent();

        if (intent != null &&
                intent.getExtras() != null) {

            if (intent.getExtras().containsKey(KEY_DETAILS_PRODUCT_BUNDLE)) {
                mProduct = intent.getExtras().getParcelable(KEY_DETAILS_PRODUCT_BUNDLE);
            }
        }
    }

    @Override
    public void showDetails(Product product) {
        Glide.with(this)
                .load(product.getThumbnailHd())
                .into(mExpandedImage);

        mTitle.setText(product.getTitle());
        mSeller.setText("By " + product.getSeller());
        mPrice.setText(product.getPriceFormatted());
        mZipCode.setText("Zipcode  " + product.getZipCode());
        mDate.setText("Published Date  " + product.getDate());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void hideError() {

    }

    private void loadViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitle = (TextView) findViewById(R.id.title);
        mSeller = (TextView) findViewById(R.id.seller);
        mPrice = (TextView) findViewById(R.id.price);
        mZipCode = (TextView) findViewById(R.id.zipcode);
        mDate = (TextView) findViewById(R.id.date);
        mExpandedImage = (ImageView) findViewById(R.id.expandedImage);

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
        MenuItem menuItem = menu.findItem(R.id.action_search);
        if (menuItem != null) {
            menuItem.setVisible(false);
        }

        return true;
    }
}
