package victorcruz.dms.get_data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import victorcruz.dms.UI.ExpandableHeightListView;
import victorcruz.dms.produto.Product;
import victorcruz.dms.produto.ProductSoreAdapter;

public class GetImage extends AsyncTask<String, Void, Bitmap> {

    int index;
    private ArrayList<Product> productsStore;

    private ExpandableHeightListView storeListView;
    private ProductSoreAdapter productSoreAdapter;

    public GetImage(ArrayList<Product> productsStore, int index, ExpandableHeightListView storeListView,
                    ProductSoreAdapter productSoreAdapter){
        this.productsStore = productsStore;
        this.index = index;

        this.storeListView = storeListView;
        this.productSoreAdapter = productSoreAdapter;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        try{
            URL url_value = new URL(params[0]);
            Bitmap image = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
            return image;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap image) {
        super.onPostExecute(image);

        productsStore.get(index).setImage(image);

        // atualiza a view
        storeListView.setAdapter(productSoreAdapter);

        System.out.println("Download de imagem com sucesso!");

    }
}