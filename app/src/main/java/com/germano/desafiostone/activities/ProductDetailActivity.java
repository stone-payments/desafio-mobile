package com.germano.desafiostone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.germano.desafiostone.Constants;
import com.germano.desafiostone.R;
import com.germano.desafiostone.Singleton;
import com.germano.desafiostone.models.Product;
import com.germano.desafiostone.presenters.ProductDetailPresenter;
import com.germano.desafiostone.presenters.ProductDetailPresenterImpl;
import com.germano.desafiostone.utils.FormatNumber;
import com.germano.desafiostone.views.ProductDetailView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailView{

    @BindView(R.id.expandedImage)
    ImageView mImageViewProductDetail;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbar;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.relative_cart)
    RelativeLayout mRelativeCart;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;

    @BindView(R.id.notification_number)
    TextView mTextViewNotificationNumber;

    @BindView(R.id.textview_title)
    TextView mTextViewTitle;

    @BindView(R.id.textview_seller)
    TextView mTextViewSeller;

    @BindView(R.id.textview_zipcode)
    TextView mTextViewZipCode;

    @BindView(R.id.textview_date)
    TextView mTextViewDate;

    @BindView(R.id.textview_price)
    TextView mTextviewPrice;

    @OnClick(R.id.btn_checkout)
    void onClickCheckout(){
        mEventHandler.onCheckoutClicked();
    }

    @OnClick(R.id.relative_cart)
    void onClickMenuCheckout(){
        mEventHandler.onMenuCheckoutClicked();
    }

    Product mProduct;

    ProductDetailPresenter mEventHandler;

    ArrayList<Product> mListProduct;

    static String TAG = "ProductDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mProduct = intent.getParcelableExtra(Constants.PRODUCT);
        mListProduct = Singleton.getInstance().getProductList();
        mEventHandler = new ProductDetailPresenterImpl();
        mEventHandler.initView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEventHandler.checkNotificationNumber(mListProduct.size());
    }

    @Override
    public void onBackPressed() {
        Singleton.getInstance().setProductList(mListProduct);
        super.onBackPressed();
    }

    @Override
    public void contentView() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.placeholder);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(mProduct.getUrlImage())
                .into(mImageViewProductDetail);

        mTextViewTitle.setText(mProduct.getTitle());
        mTextViewSeller.setText(mProduct.getSeller());
        mTextViewZipCode.setText(mProduct.getZipcode());
        mTextViewDate.setText(mProduct.getDate());
        mTextviewPrice.setText(getString(R.string.total_value, FormatNumber.set(mProduct.getPrice())));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText(getString(R.string.title_ad));
        setupAppBar();
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }


    @Override
    public void showCheckout() {
        Intent intent = new Intent(this, CartCheckoutActivity.class);
        mListProduct.add(mProduct);
        intent.putParcelableArrayListExtra(Constants.PRODUCT_LIST, mListProduct);
        startActivity(intent);
        Log.i(TAG, "Added a Product");
    }

    @Override
    public void enableNotification(int number) {
        mTextViewNotificationNumber.setVisibility(View.VISIBLE);
        mTextViewNotificationNumber.setText(String.valueOf(number));
        Log.i(TAG, "Total Items:" + number);
    }

    @Override
    public void hideNotification() {
        mTextViewNotificationNumber.setVisibility(View.GONE);
    }

    @Override
    public void showCheckoutMenu() {
        Intent intent = new Intent(this, CartCheckoutActivity.class);
        intent.putParcelableArrayListExtra(Constants.PRODUCT_LIST, mListProduct);
        startActivity(intent);
    }


    private void setupAppBar(){
        mAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                mRelativeCart.setVisibility(View.VISIBLE);
                mToolbarTitle.setVisibility(View.VISIBLE);
                animateFade(mRelativeCart);
                animateFade(mToolbarTitle);
            } else if (verticalOffset == 0) {
                mRelativeCart.setVisibility(View.INVISIBLE);
                mToolbarTitle.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void animateFade(View view){
        view.setAlpha(0);
        view.animate()
                .alpha(1)
                .setDuration(400)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }
}
