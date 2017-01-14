package com.am.store.starwars.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.am.store.starwars.R;
import com.am.store.starwars.StarWarStoreActivity;
import com.am.store.starwars.exception.StarWarsException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.helper.formatter.CurrencyFormatter;
import com.am.store.starwars.model.store.product.Product;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Augusto on 14/01/2017.
 */

public class ProductViewAdapter extends BaseAdapter implements ListAdapter {

    private static final String LOG_CONSTANT = ProductViewAdapter.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    private Context context;
    private List<Product> products;
    private LayoutInflater mInflater = null;

    public ProductViewAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    public ProductViewAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    public void addProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        if (position > 0) {
            return products.get(position);
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

            convertView = mInflater.inflate(R.layout.product_line_buy_layout, null);
        }

        ImageView imgProduct = (ImageView) convertView.findViewById(R.id.productLine_imgProduct);
        TextView txtAmount = (TextView) convertView.findViewById(R.id.productLine_amout);
        TextView txtProduct = (TextView) convertView.findViewById(R.id.productLine_product);
        TextView txtVendor = (TextView) convertView.findViewById(R.id.productLine_vendor);

        try {
            Product product = products.get(position);
            txtAmount.setText(CurrencyFormatter.transformToCurrency(product.getPrice()));
            txtVendor.setText(product.getSeller());
            txtProduct.setText(product.getTitle());
        } catch (StarWarsException e) {
            logger.error(LOG_CONSTANT, "Problems to format data for View", e);
        }

        return convertView;
    }
}