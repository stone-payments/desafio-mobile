package com.jademcosta.starstore.cart;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jademcosta.starstore.R;

public class CartActivity extends AppCompatActivity implements CartContract.View {

    

    public static Intent newIntent(Context context) {
        return new Intent(context.getApplicationContext(), CartActivity.class);
    }
}
