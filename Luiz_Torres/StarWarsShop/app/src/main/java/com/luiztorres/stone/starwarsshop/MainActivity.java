package com.luiztorres.stone.starwarsshop;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.luiztorres.stone.starwarsshop.extras.ScreenManager;
import com.luiztorres.stone.starwarsshop.fragments.CartFragment;
import com.luiztorres.stone.starwarsshop.fragments.CheckoutFragment;
import com.luiztorres.stone.starwarsshop.fragments.OrdersFragment;
import com.luiztorres.stone.starwarsshop.fragments.ShopFragment;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new ShopFragment();

        ScreenManager.getInstance(this).Replace(fragment);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_bottom_cart:
                                    fragment = new CartFragment();
                                break;
                            case R.id.action_bottom_shopping:
                                    fragment = new ShopFragment();
                                break;
                            case R.id.action_bottom_orders:
                                    fragment = new OrdersFragment();
                                break;
                        }

                        ScreenManager.getInstance(MainActivity.this).Replace(fragment);

                        return false;
                    }
                });
    }
}
