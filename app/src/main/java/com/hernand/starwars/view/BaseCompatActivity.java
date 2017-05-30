package com.hernand.starwars.view;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hernand.starwars.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

/**
 * Created by Nando on 28/05/2017.
 */

public abstract class BaseCompatActivity extends AppCompatActivity implements Validator.ValidationListener {

    public static final String EXTRA_TRANSACOES = "com.hernand.starwars.transactions";
    public static final String EXTRA_PRODUTOS = "com.hernand.starwars.products";
    public static final String EXTRA_IMAGE = "com.hernand.starwars.image";
    public static final String EXTRA_PRODUTO = "com.hernand.starwars.product";

    protected void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    protected void initToolbar(String title){

        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);

        if(title != null)
            toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
