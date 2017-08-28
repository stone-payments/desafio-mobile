package com.germano.desafiostone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.germano.desafiostone.Constants;
import com.germano.desafiostone.R;
import com.germano.desafiostone.Singleton;
import com.germano.desafiostone.adapters.ProductAdapter;
import com.germano.desafiostone.models.Product;
import com.germano.desafiostone.presenters.CartCheckoutPresenter;
import com.germano.desafiostone.presenters.CartCheckoutPresenterImpl;
import com.germano.desafiostone.utils.FormatNumber;
import com.germano.desafiostone.views.CartCheckoutView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartCheckoutActivity extends AppCompatActivity implements CartCheckoutView {

    @BindView(R.id.textview_total_item)
    TextView mTextViewTotalItems;

    @BindView(R.id.textview_total_value)
    TextView mTextViewTotalValue;

    @BindView(R.id.recyclerview_checkout)
    RecyclerView mRecyclerViewCheckout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    CartCheckoutPresenter mEventHandler;

    ProductAdapter mAdapter;

    Intent mIntent;

    ArrayList<Product> mProductList;

    @OnClick(R.id.textview_checkout)
    void onClickCheckout(){
        mEventHandler.onClickCheckout(mProductList.size());
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);
        ButterKnife.bind(this);
        mIntent = getIntent();
        mProductList = mIntent.getParcelableArrayListExtra(Constants.PRODUCT_LIST);
        if(mProductList == null){
            mProductList = Singleton.getInstance().getProductList();
        }

        mEventHandler = new CartCheckoutPresenterImpl();
        mEventHandler.initView(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.PRODUCT_LIST, mProductList);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.PRODUCT_LIST, mProductList);
    }

    @Override
    public void contentView() {
        mTextViewTotalValue.setText(getString(R.string.total_value, FormatNumber.set(checkTotalValue())));
        mTextViewTotalItems.setText(getString(R.string.total_items, mProductList.size()));

        mToolbar.setTitle(getString(R.string.title_checkout));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showCardPayment() {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(Constants.TOTAL_VALUE, checkTotalValue());
            startActivity(intent);
    }

    @Override
    public void setupRecyclerView() {
        mAdapter = new ProductAdapter(this, mProductList, "cart");
        mAdapter.setOnDeleteClicked((mEventHandler::onDeleteClicked));
        mRecyclerViewCheckout.setAdapter(mAdapter);
        mRecyclerViewCheckout.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void deleteItemFromList(int position) {
        mProductList.remove(position);
        Singleton.getInstance().setProductList(mProductList);
        mAdapter.notifyItemRemoved(position);
        mEventHandler.updateView();
    }


    private int checkTotalValue(){
        int totalValue = 0;

        for(int i = 0; i<mProductList.size();i++){
           totalValue += mProductList.get(i).getPrice();
        }

       return totalValue;
    }
}
