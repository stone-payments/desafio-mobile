package payments.stone.com.br.desafiomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    public static final String KEY_DETAILS_PRODUCT_BUNDLE = "DETAILS_PRODUCT_BUNDLE";
    private Product product;

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


        if (intent != null &&
                intent.getExtras() != null) {

            if (intent.getExtras().containsKey(KEY_DETAILS_PRODUCT_BUNDLE)) {
                product = intent.getExtras().getParcelable(KEY_DETAILS_PRODUCT_BUNDLE);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

//        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent)); // transperent color = #00000000
//        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));
//        collapsingToolbarLayout.title(product.getTitle());


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        ImageView expandedImage = (ImageView) findViewById(R.id.expandedImage);
        mTitle = (TextView) findViewById(R.id.title);
        mSeller = (TextView) findViewById(R.id.seller);
        mPrice = (TextView) findViewById(R.id.price);
        mZipCode = (TextView) findViewById(R.id.zipcode);
        mDate = (TextView) findViewById(R.id.date);

        Glide.with(this)
                .load(product.getThumbnailHd())
                .into(expandedImage);

        mTitle.setText(product.getTitle());
        mSeller.setText("By " + product.getSeller());
        mPrice.setText("R$" + String.format("%.2f", product.getPrice() / 1000.0));
        mZipCode.setText("Zipcode  " + product.getZipCode());
        mDate.setText("Published Date  " + product.getDate());


//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbarLayout.title("Jedi Details");
//                    isShow = true;
//                } else if (isShow) {
//                    collapsingToolbarLayout.title(" ");//carefull there should a space between double quote otherwise it wont work
//                    isShow = false;
//                }
//            }
//        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_cart:
                startActivity(new Intent(this, CartActivity.class));
                return true;


            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_search);
        if (menuItem != null) {
            menuItem.setVisible(false);
        }

        return true;
    }
}
