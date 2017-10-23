package com.stone.mobile.stonestore;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.stone.mobile.stonestore.Fragments.CarrinhoFragment;
import com.stone.mobile.stonestore.Fragments.FinalizarCompraFragment;
import com.stone.mobile.stonestore.Fragments.HistoricoFragment;
import com.stone.mobile.stonestore.Fragments.LojaFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CarrinhoFragment.CarrinhoFinalizarCompra,
        FinalizarCompraFragment.VotlarAoHistorico {

    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();

        mudarFragment(new LojaFragment());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_loja) {
            mudarFragment(new LojaFragment());
        } else if (id == R.id.nav_carrinho) {
            mudarFragment(new CarrinhoFragment());
        } else if (id == R.id.nav_historico) {
            mudarFragment(new HistoricoFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void mudarFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.frame_fragment, fragment).commit();
    }

    @Override
    public void finalizarCompraFragment(int total) {
        FinalizarCompraFragment finalizarCompraFragment = new FinalizarCompraFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("total", total);
        finalizarCompraFragment.setArguments(bundle);

        mudarFragment(finalizarCompraFragment);
    }

    @Override
    public void voltarHistorico() {
        mudarFragment(new HistoricoFragment());
    }
}
