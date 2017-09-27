package payments.stone.com.br.desafiomobile.order;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import payments.stone.com.br.desafiomobile.R;
import payments.stone.com.br.desafiomobile.model.Order;
import payments.stone.com.br.desafiomobile.views.BaseActivity;

public class OrderActivity extends BaseActivity implements OrderView {

    private RecyclerView mOrderRecyclerView;
    private OrderAdapter mAdapter;

    private OrderPresenter mPresenter;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        loadViews();

        mPresenter = new OrderPresenter(this)
                        .loadOrders();
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void hideError() {

    }

    @Override
    public Object handleIntent() {
        return null;
    }

    @Override
    public void loadViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOrderRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.order_progress_bar);
    }

    @Override
    public void showOrders(List<Order> orders) {
        mAdapter = new OrderAdapter(this, orders, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mOrderRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mOrderRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        mOrderRecyclerView.addItemDecoration(dividerItemDecoration);
        mOrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mOrderRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem search = menu.findItem(R.id.action_search);
        if (search != null) {
            search.setVisible(false);
        }


        MenuItem reset =  menu.findItem(R.id.action_reset_cart);
        if(reset!=null){
            reset.setVisible(false);
        }

        MenuItem cart =  menu.findItem(R.id.action_show_cart);
        if(cart!=null){
            cart.setVisible(false);
        }

        return true;
    }

}
