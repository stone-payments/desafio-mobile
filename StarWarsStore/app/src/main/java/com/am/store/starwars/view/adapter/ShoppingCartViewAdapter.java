package com.am.store.starwars.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.store.starwars.R;
import com.am.store.starwars.core.ShoppingCartManager;
import com.am.store.starwars.dao.ProductImageDAO;
import com.am.store.starwars.exception.StarWarServiceException;
import com.am.store.starwars.exception.StarWarsException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.helper.formatter.CurrencyFormatter;
import com.am.store.starwars.model.store.product.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Augusto on 14/01/2017.
 */

public class ShoppingCartViewAdapter extends BaseAdapter {

    private static final String LOG_CONSTANT = ProductViewAdapter.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    private Context context;
    private List<Product> products;
    private LayoutInflater mInflater = null;
    private ShoppingCartManager shoppingCartManager;
    private ProductImageDAO imageDAO;


    public ShoppingCartViewAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        this.shoppingCartManager = new ShoppingCartManager();
        this.imageDAO = new ProductImageDAO();
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ShoppingCartViewAdapter.ViewHolder holder;
        if (convertView != null) {
            holder = (ShoppingCartViewAdapter.ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.product_line_cart_layout, parent, false);
            holder = new ShoppingCartViewAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Product product = null;
        try {
            product = products.get(position);
            holder.txtAmount.setText(CurrencyFormatter.transformToCurrency(product.getPrice()));
            holder.txtVendor.setText(product.getSeller());
            holder.txtProduct.setText(product.getTitle());

            Bitmap bitmap = imageDAO.getImage(product.getTitle());
            holder.imgProduct.setImageBitmap(bitmap);
        } catch (StarWarsException e) {
            logger.error(LOG_CONSTANT, "Problems to format data for View", e);
        }

        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    shoppingCartManager.deleteProduct(products.get(position));
                    products = shoppingCartManager.getShoppingCart();
                    notifyDataSetChanged();
                } catch (StarWarServiceException e) {
                    logger.error("Problems to delete product from ShoppingCart!", e);
                }
            }
        });

        return convertView;
    }


    static class ViewHolder {

        @BindView(R.id.productLine_imgProduct)
        ImageView imgProduct;

        @BindView(R.id.productLine_amout)
        TextView txtAmount;

        @BindView(R.id.productLine_product)
        TextView txtProduct;

        @BindView(R.id.productLine_vendor)
        TextView txtVendor;

        @BindView(R.id.productLine_btnDelete)
        ImageButton btnBuy;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}