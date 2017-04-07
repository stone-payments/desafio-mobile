package mobile.desafio.stone.desafiomobile;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductListBaseAdapter extends BaseAdapter {

    private ArrayList<Product> allProducts;
    private LayoutInflater layoutInflater;
    private Context context;

    public ProductListBaseAdapter(Context context, ArrayList<Product> products){
        this.allProducts = products;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.product_details_view, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.price = (TextView)convertView.findViewById(R.id.price);
            viewHolder.seller = (TextView)convertView.findViewById(R.id.seller);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder =  (ViewHolder)convertView.getTag();
        }

        Product product = this.allProducts.get(position);
        Picasso.with(context).load(product.getThumbnailHd()).resize(100,100).centerCrop().into(viewHolder.image);
        viewHolder.title.setText(product.getTitle());
        viewHolder.price.setText("R$ " + product.getPrice());
        viewHolder.seller.setText("Vendedor: " + product.getSeller());

        return convertView;
    }

    public void clear(){
        this.allProducts.clear();
    }

    public void addEntireData(ArrayList<Product> products){
        this.allProducts = products;
    }

    static class ViewHolder {
        ImageView image;
        TextView title;
        TextView price;
        TextView seller;
        Spinner spinner;
    }
}
