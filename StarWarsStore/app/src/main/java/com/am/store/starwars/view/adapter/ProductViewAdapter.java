package com.am.store.starwars.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.am.store.starwars.R;
import com.am.store.starwars.core.ShoppingCartManager;
import com.am.store.starwars.exception.StarWarServiceException;
import com.am.store.starwars.exception.StarWarsException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.helper.BitmapDownloaderTask;
import com.am.store.starwars.helper.formatter.CurrencyFormatter;
import com.am.store.starwars.integration.store.vo.ProductVO;
import com.am.store.starwars.model.store.product.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Augusto on 14/01/2017.
 */

public class ProductViewAdapter extends BaseAdapter implements ListAdapter {

    private static final String LOG_CONSTANT = ProductViewAdapter.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    private Context context;
    private List<ProductVO> products;
    private LayoutInflater mInflater = null;

    private ShoppingCartManager shoppingCartManager;

    public ProductViewAdapter(Context context, List<ProductVO> products) {
        this.context = context;
        this.products = products;
        this.shoppingCartManager = new ShoppingCartManager();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.product_line_buy_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        ProductVO product = null;
        try {
            product = products.get(position);
            holder.txtAmount.setText(CurrencyFormatter.transformToCurrency(product.getPrice()));
            holder.txtVendor.setText(product.getSeller());
            holder.txtProduct.setText(product.getTitle());
        } catch (StarWarsException e) {
            logger.error(LOG_CONSTANT, "Problems to format data for View", e);
        }

        holder. btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    shoppingCartManager.addItem(new Product(products.get(position)));
                } catch (StarWarServiceException e) {
                    logger.error(LOG_CONSTANT, "Problems to insert product in Shopping Cart", e);
                }
            }
        });

        try {
            download(product.getImageEndpoint(), holder.imgProduct, product);
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems downloading Bitmap for ProductVO " + product.getTitle());
        }

        return convertView;
    }

    public void download(String url, ImageView imageView, ProductVO product) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
        BitmapDownloaderTask.DownloadedDrawable downloadedDrawable = new BitmapDownloaderTask.DownloadedDrawable(task);
        imageView.setImageDrawable(downloadedDrawable);
        imageView.setMinimumHeight(156);
        task.execute(url, product.getTitle());
    }

    static class ViewHolder {

        @BindView(R.id.productLine_imgProduct)
        protected ImageView imgProduct;

        @BindView(R.id.productLine_amout)
        protected TextView txtAmount;

        @BindView(R.id.productLine_product)
        protected TextView txtProduct;

        @BindView(R.id.productLine_vendor)
        protected TextView txtVendor;

        @BindView(R.id.productLine_btnBuy)
        protected Button btnBuy;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}