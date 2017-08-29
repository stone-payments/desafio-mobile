package com.germano.desafiostone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.germano.desafiostone.R;
import com.germano.desafiostone.Singleton;
import com.germano.desafiostone.fragments.HistoryListFragment;
import com.germano.desafiostone.fragments.ProductListFragment;
import com.germano.desafiostone.models.Product;
import com.germano.desafiostone.presenters.HomePresenter;
import com.germano.desafiostone.presenters.HomePresenterImpl;
import com.germano.desafiostone.views.HomeActivityView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeActivityView {

    @BindView(R.id.bottom_bar)
    BottomNavigationView mBottomBar;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    HomePresenter mEventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mEventHandler = new HomePresenterImpl();
        mEventHandler.initView(this);
    }

    @Override
    public void setupBottomBar() {
        mOnNavigationItemSelectedListener = item -> {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new ProductListFragment();
                    mToolBar.setTitle(R.string.title_product);
                    break;
                case R.id.navigation_dashboard:
                    fragment = null;
                    if(Singleton.getInstance().getProductList().size() != 0){
                        startActivity(new Intent(this, CartCheckoutActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.error_empty_cart),
                                Toast.LENGTH_SHORT).show();
                    }
                    return false;
                case R.id.navigation_notifications:
                    fragment = new HistoryListFragment();
                    mToolBar.setTitle(R.string.title_history);
                    break;
                default:
                    return false;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
            return true;
        };
    }

    @Override
    public void contentView() {
        mBottomBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBottomBar.setSelectedItemId(R.id.navigation_home);
    }
}
