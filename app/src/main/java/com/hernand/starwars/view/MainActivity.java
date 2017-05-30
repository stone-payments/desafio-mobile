package com.hernand.starwars.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hernand.starwars.R;
import com.hernand.starwars.adapters.RecyclerViewAdapter;
import com.hernand.starwars.controller.MainController;
import com.hernand.starwars.model.Transact;
import com.hernand.starwars.util.CartSingleton;
import com.hernand.starwars.util.CircleTransform;
import com.hernand.starwars.util.ConectivityHelper;
import com.hernand.starwars.vo.ProductVO;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nando on 27/05/2017.
 */

public class MainActivity extends BaseCompatActivity implements RecyclerViewAdapter.OnItemClickListener {

    public static final String AVATAR_URL = "http://myapplemagazine.com/assets/defaults/small_user_avatar-dbeb63d7ce9479c5a404696e61e735620a48d889625d217e98ed9c42ea0dc05b.png";

    private ProgressDialog statusDialog;
    private ConectivityHelper conectivityHelper;
    private DrawerLayout drawerLayout;
    private View content;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private List<ProductVO> listaProdutos;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainController();
        initFab();
        initToolbar();
        setupDrawerLayout();
        initComponents();

        final ImageView avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar);
        Picasso.with(this).load(AVATAR_URL).transform(new CircleTransform()).into(avatar);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setRecyclerAdapter();
        }
    }

    /*
    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        setRecyclerAdapter();
        recyclerView.scheduleLayoutAnimation();
    }
    */

    public void initComponents(){
        conectivityHelper = new ConectivityHelper(this);
        statusDialog = new ProgressDialog(this);
        content = findViewById(R.id.content);
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
        setRecyclerAdapter();
        recyclerView.scheduleLayoutAnimation();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        int lineElements = 1;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            lineElements = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(this, lineElements));

    }

    private void setRecyclerAdapter() {

        if(!conectivityHelper.isConected()){
            Toast.makeText(this, getString(R.string.verifique_conexao_internet), Toast.LENGTH_LONG).show();
        }else {
            chamarApiRestProdutos();
        }
    }

    public void chamarApiRestProdutos() {
        statusDialog.setMessage(getString(R.string.carregando));
        statusDialog.show();
        controller.callApiRestProdutos(new Callback<List<ProductVO>>() {
            @Override
            public void onResponse(Call<List<ProductVO>> call, Response<List<ProductVO>> response) {
                listaProdutos = response.body();
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(listaProdutos,MainActivity.this);
                adapter.setOnItemClickListener(MainActivity.this);
                recyclerView.setAdapter(adapter);
                statusDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<ProductVO>> listCall, Throwable t) {
                Toast.makeText(MainActivity.this,getString(R.string.verifique_conexao_internet), Toast.LENGTH_LONG).show();
                statusDialog.dismiss();
            }
        });
    }

    /**
     * Inicializa o botao para entrar no carrinho
     */
    private void initFab() {
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                CartSingleton carrinho = CartSingleton.getInstance();
                CheckoutActivity.navigate(MainActivity.this,carrinho.getProdutos());
            }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){

                    case R.id.drawer_finalizar_compra :
                        CheckoutActivity.navigate(MainActivity.this,controller.getProdutos());
                        break;
                    case R.id.drawer_historico_transacoes :
                        List<Transact> transacts = controller.listTransactions();
                        TransactionsActivity.navigate(MainActivity.this,transacts);
                        break;

                    default:

                        break;
                }


                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public void onItemClick(View view, ProductVO viewModel) {
        DetailActivity.navigate(this, view.findViewById(R.id.image), viewModel);
    }

    public static void navigate(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        ActivityCompat.startActivity(activity,intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }
}
