package victorcruz.dms.produto;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import victorcruz.dms.R;


public class ProductSoreAdapter extends BaseAdapter {

    private final ArrayList<Product> products;
    private final Activity act;

    public ProductSoreAdapter(ArrayList<Product> products, Activity act){
        this.products = products;
        this.act = act;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_store, parent, false);

        Product product = products.get(position);

        // seta os dados de cada produto em seu componente
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView seller = (TextView) view.findViewById(R.id.seller);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        image.setImageBitmap(product.getImage());

        title.setText(product.getTitle());

        DecimalFormat decimalFormat = new DecimalFormat("#,#####,00");
        String valor = decimalFormat.format((double) product.getPrice());
        valor = "R$ " + valor;
        price.setText(valor);

        String _seller = product.getSeller();
        _seller = "de " + _seller;
        seller.setText(_seller);

        return view;
    }
}
