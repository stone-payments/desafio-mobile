package com.am.store.starwars.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.am.store.starwars.R;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.helper.converter.DateConveter;
import com.am.store.starwars.helper.formatter.CurrencyFormatter;
import com.am.store.starwars.model.store.product.Purchase;

import java.util.List;

/**
 * Created by Augusto on 15/01/2017.
 */

public class PurchaseViewAdapter extends BaseAdapter {

    private static final String LOG_CONSTANT = ProductViewAdapter.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    private Context context;
    private List<Purchase> purchases;
    private LayoutInflater mInflater = null;

    private CurrencyFormatter currencyFormatter;
    private DateConveter dateConveter;

    public PurchaseViewAdapter(Context context, List<Purchase> purchases) {
        this.context = context;
        this.purchases = purchases;
        this.currencyFormatter = new CurrencyFormatter();
        this.dateConveter = new DateConveter();

        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return purchases.size();
    }

    @Override
    public Object getItem(int position) {
        if (position > 0) {
            return purchases.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.purchases_line_layout, null);
        }

        try {
            Purchase purchase = purchases.get(position);

            TextView txtAmounmt = (TextView) convertView.findViewById(R.id.purchase_amout);
            TextView txtDate = (TextView) convertView.findViewById(R.id.purchase_date);
            TextView txtHour = (TextView) convertView.findViewById(R.id.purchase_hour);

            txtAmounmt.setText(CurrencyFormatter.transformToCurrency(String.valueOf(purchase.getAmount())));
            txtDate.setText(dateConveter.extractDate(purchase.getDateTime(), DateConveter.MMDD));
            txtHour.setText(dateConveter.extractDate(purchase.getDateTime(), DateConveter.HHmm));

        } catch (Exception e) {
            //TODO:
        }


        return convertView;
    }
}