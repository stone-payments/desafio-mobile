package payments.stone.com.br.desafiomobile.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import payments.stone.com.br.desafiomobile.model.Product;
import payments.stone.com.br.desafiomobile.R;
import payments.stone.com.br.desafiomobile.details.DetailsPresenter;
import payments.stone.com.br.desafiomobile.details.DetailsView;
import payments.stone.com.br.desafiomobile.views.BaseActivity;

public class DetailsActivity extends BaseActivity implements DetailsView {
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

    @Override
    public Product handleIntent() {
        Intent intent = getIntent();

        if (intent != null &&
                intent.getExtras() != null) {

            if (intent.getExtras().containsKey(KEY_DETAILS_PRODUCT_BUNDLE)) {
                mProduct = intent.getExtras().getParcelable(KEY_DETAILS_PRODUCT_BUNDLE);
            }
        }

        return mProduct;
    }

    @Override
    public void loadViews() {
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
}
