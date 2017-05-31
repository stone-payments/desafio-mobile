package com.hernand.starwars.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hernand.starwars.R;
import com.hernand.starwars.util.CartSingleton;
import com.hernand.starwars.vo.ProductVO;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Nando on 27/05/2017.
 */

public class DetailActivity extends BaseCompatActivity {


    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProductVO produtoVO;

    private TextView title,productseller,productprice;

    public static void navigate(AppCompatActivity activity, View transitionImage, ProductVO viewModel) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, viewModel.getThumbnailHd());
        intent.putExtra(EXTRA_PRODUTO, viewModel);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @SuppressWarnings("ConstantConditions")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_detail);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initFab();
        initComponents();

    }


    private void initComponents(){
        produtoVO = (ProductVO) getIntent().getSerializableExtra(EXTRA_PRODUTO);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(produtoVO.getTitle());
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        final ImageView image = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(image, new Callback() {
            @Override public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override public void onError() {

            }
        });


        productprice = (TextView) findViewById(R.id.productprice);
        productseller = (TextView) findViewById(R.id.productseller);
        title = (TextView) findViewById(R.id.title);
        title.setText(produtoVO.getTitle());
        productprice.setText(String.format("R$ %s",produtoVO.getPrice().toString()));
        productseller.setText(produtoVO.getSeller());
    }
    private void initFab() {
        findViewById(R.id.fabdetail).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            CartSingleton carrinho = CartSingleton.getInstance();
            carrinho.getProdutos().add(produtoVO);
            Toast.makeText(DetailActivity.this,getString(R.string.adicionado_carrinho), Toast.LENGTH_LONG).show();
            finish();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.primary_dark);
        int primary = getResources().getColor(R.color.primary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        updateBackground((FloatingActionButton) findViewById(R.id.fabdetail), palette);
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.accent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }
}
