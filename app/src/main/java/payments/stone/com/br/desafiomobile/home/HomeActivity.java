package payments.stone.com.br.desafiomobile.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import payments.stone.com.br.desafiomobile.DetailsActivity;
import payments.stone.com.br.desafiomobile.Navigation;
import payments.stone.com.br.desafiomobile.Product;
import payments.stone.com.br.desafiomobile.R;
import payments.stone.com.br.desafiomobile.Utils;
import payments.stone.com.br.desafiomobile.cart.CartActivity;
import payments.stone.com.br.desafiomobile.cart.ProductsAdapter;
import payments.stone.com.br.desafiomobile.cart.ProductsResponse;

import static payments.stone.com.br.desafiomobile.DetailsActivity.KEY_DETAILS_PRODUCT_BUNDLE;

public class HomeActivity extends AppCompatActivity implements HomeView, NavigationView.OnNavigationItemSelectedListener, Navigation {
    private List<Product> mProductList = new ArrayList<>();
    private RecyclerView mProductsRecyclerView;
    private ProductsAdapter mAdapter;

    private HomePresenter mPresenter;

    private ImageView mBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        mPresenter = new HomePresenter(this);

        loadViews();
        loadProducts();
    }

    private void loadViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        mBackdrop = (ImageView) findViewById(R.id.backdrop);
        mProductsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProducts(List<Product> productList) {
//                Glide
//                .with(this)
//                .load(R.drawable.yoda_cover)
//                .into(mBackdrop);

        mAdapter = new ProductsAdapter(this, productList, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mProductsRecyclerView.setLayoutManager(mLayoutManager);
        mProductsRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(2, getApplicationContext()), true));
        mProductsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProductsRecyclerView.setAdapter(mAdapter);

//        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void hideError() {

    }

    private void loadProducts() {
        new ProductAsyncTask(mPresenter, getApplicationContext()).execute();
//        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_order) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void whenGoToDetails(Product product) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(KEY_DETAILS_PRODUCT_BUNDLE, product);
        startActivity(intent);
    }

    public static class ProductAsyncTask extends AsyncTask<Void, Void, List<Product>> {
        private HomePresenter presenter;
        private List<Product> products;
        private Context context;

        public ProductAsyncTask(HomePresenter presenter, Context context) {
            this.presenter = presenter;
            this.context = context;
        }

        @Override
        protected List<Product> doInBackground(Void... params) {
            Type productListType = new TypeToken<ArrayList<Product>>() {
            }.getType();
            products = new Gson().fromJson(Utils.loadJSONFromAsset(context), productListType);
            return products;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            presenter.onProductsReceived(new ProductsResponse(products));
        }
    }
}
