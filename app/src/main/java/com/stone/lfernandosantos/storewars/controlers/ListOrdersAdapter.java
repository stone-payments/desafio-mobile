package com.stone.lfernandosantos.storewars.controlers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.stone.lfernandosantos.storewars.R;
import com.stone.lfernandosantos.storewars.models.Order;

import java.util.List;

/**
 * Created by lf.fernandodossantos on 23/05/17.
 */

public class ListOrdersAdapter extends BaseAdapter {

    Activity activity;
    List<Order> orders;

    public ListOrdersAdapter(Activity activity, List<Order> orders) {
        this.activity = activity;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orders.get(position).idOrder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = activity.getLayoutInflater().inflate(R.layout.item_order_adapter, null);

        TextView order = (TextView) v.findViewById(R.id.txtNumberOrderAdp);
        TextView qtdItens = (TextView) v.findViewById(R.id.txtQtdItensOrderAdp);
        TextView date = (TextView) v.findViewById(R.id.txtDateOrderAdp);
        TextView total = (TextView) v.findViewById(R.id.txtTotalOrderAdp);

        order.setText(String.valueOf(orders.get(position).idOrder));
        date.setText(orders.get(position).date);
        total.setText("R$ " + orders.get(position).getPrice());
        qtdItens.setText(orders.get(position).itens);
        return v;
    }
}
