package payments.stone.com.br.desafiomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    public static final String KEY_DETAILS_PRODUCT_BUNDLE = "DETAILS_PRODUCT_BUNDLE";
    private  Product product;

    private TextView mTitle;
    private TextView mSeller;
    private TextView mPrice;
    private TextView mZipCode;
    private TextView mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        Intent intent = getIntent();


        if(intent!=null &&
                intent.getExtras()!= null){

            if(intent.getExtras().containsKey(KEY_DETAILS_PRODUCT_BUNDLE)){
                product = intent.getExtras().getParcelable(KEY_DETAILS_PRODUCT_BUNDLE);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

//        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent)); // transperent color = #00000000
//        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));
        collapsingToolbarLayout.setTitle(product.getTitle());


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        ImageView expandedImage = (ImageView) findViewById(R.id.expandedImage);
        mTitle = (TextView) findViewById(R.id.title);
        mSeller = (TextView) findViewById(R.id.seller);
        mPrice = (TextView)findViewById(R.id.price);
        mZipCode = (TextView) findViewById(R.id.zipcode);
        mDate = (TextView) findViewById(R.id.date);

        Glide.with(this)
                .load(product.getThumbnailHd())
                .into(expandedImage);

        mTitle.setText(product.getTitle());
        mSeller.setText("By " + product.getSeller());
        mPrice.setText("R$" + Math.round( product.getPrice() /1000));
        mZipCode.setText("Zipcode  " + product.getZipCode());
        mDate.setText("Published Date  " + product.getDate());



        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbarLayout.setTitle("Jedi Details");
//                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}
