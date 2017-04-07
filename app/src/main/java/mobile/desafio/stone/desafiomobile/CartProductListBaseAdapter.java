package mobile.desafio.stone.desafiomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gerson on 06/04/2017.
 */

public class CartProductListBaseAdapter extends BaseAdapter {

    private ArrayList<CartProduct> allProducts;
    private LayoutInflater layoutInflater;
    private SpinnerListener spinnerListener;

    public CartProductListBaseAdapter(Context context, ArrayList<CartProduct> products, SpinnerListener spinnerListener) {
        this.allProducts = products;
        layoutInflater = LayoutInflater.from(context);
        this.spinnerListener = spinnerListener;
    }

    @Override
    public int getCount() {
        return this.allProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return this.allProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductListBaseAdapter.ViewHolder viewHolder;
        final int auxPosition = position;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cartproduct_details_view, null);
            viewHolder = new ProductListBaseAdapter.ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.cartList_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.cartList_title);
            viewHolder.price = (TextView) convertView.findViewById(R.id.cartList_price);
            viewHolder.spinner = (Spinner) convertView.findViewById(R.id.spinner);
            convertView.setTag(viewHolder);

            viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinnerListener.onSpinnerItemSelected(auxPosition, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            viewHolder = (ProductListBaseAdapter.ViewHolder) convertView.getTag();
        }

        Product product = this.allProducts.get(position);
        viewHolder.title.setText(product.getTitle());
        viewHolder.price.setText("R$ " + product.getPrice());

        return convertView;
    }

    public void clear() {
        this.allProducts.clear();
    }

    public void addEntireData(ArrayList<CartProduct> products) {
        this.allProducts = products;
    }


}