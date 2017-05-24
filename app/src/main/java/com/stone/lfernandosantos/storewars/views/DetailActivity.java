package com.stone.lfernandosantos.storewars.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stone.lfernandosantos.storewars.R;
import com.stone.lfernandosantos.storewars.models.Product;
import com.stone.lfernandosantos.storewars.models.ProductDAO;

public class DetailActivity extends AppCompatActivity {

    Product product;

    private ImageView imgProduct;
    private TextView txtTitle;
    private TextView txtSeller;
    private TextView txtPrice;
    private TextView txtZipecode;
    private TextView txtDate;
    private Button btnComprar;
    private Button btnCarrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");

        findViews();

        product = (Product) getIntent().getSerializableExtra("product");

        setViews();

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductDAO dao = new ProductDAO(DetailActivity.this);
                product.carrinho = "1";
                dao.saveProduct(product);
                dao.close();

                Intent goBuyIntent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(goBuyIntent);
                finish();
            }
        });

        btnCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductDAO dao = new ProductDAO(DetailActivity.this);
                product.carrinho = "1";
                dao.saveProduct(product);
                dao.close();
                Snackbar.make(btnCarrinho, "Item adcionado!", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setViews() {
        if (product != null){

            Glide.with(this).load(product.thumbnailHd).into(imgProduct);
            txtTitle.setText(product.title);
            txtSeller.setText(product.seller);
            txtPrice.setText("R$ "+ product.getPrice());
            txtDate.setText("Data de Publicação: " + product.date);
            txtZipecode.setText("CEP: " + product.zipcode);

        }
    }

    private void findViews() {
        imgProduct = (ImageView) findViewById(R.id.imgProductDetail);
        txtTitle = (TextView) findViewById(R.id.txtViewTitleDetail);
        txtSeller = (TextView) findViewById(R.id.txtViewSellerDetail);
        txtPrice = (TextView) findViewById(R.id.txtViewPriceDetail);
        txtDate = (TextView) findViewById(R.id.txtViewDateDetail);
        txtZipecode = (TextView) findViewById(R.id.txtViewZipcodeDetail);
        btnComprar = (Button) findViewById(R.id.btnComprar);
        btnCarrinho = (Button) findViewById(R.id.btnAddCarrinho);
    }

}
