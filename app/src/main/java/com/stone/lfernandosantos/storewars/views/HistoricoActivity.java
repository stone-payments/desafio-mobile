package com.stone.lfernandosantos.storewars.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.stone.lfernandosantos.storewars.R;
import com.stone.lfernandosantos.storewars.controlers.ListOrdersAdapter;
import com.stone.lfernandosantos.storewars.models.Order;
import com.stone.lfernandosantos.storewars.models.OrderDAO;

import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Lista de Pedidos");

        listView = (ListView) findViewById(R.id.listViewOrders);

        OrderDAO dao = new OrderDAO(this);
        List<Order> orders = dao.getOrders();

        if (orders != null && orders.size() > 0){
            ListOrdersAdapter adapter = new ListOrdersAdapter(this, orders);
            listView.setAdapter(adapter);
        }

    }

}
