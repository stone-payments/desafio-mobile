package victorcruz.dms.get_post_data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;

import victorcruz.dms.produto.ProductHandler;

public class GetImage extends AsyncTask<String, Void, Bitmap> {

    int index;

    private ProductHandler productHandler;

    public GetImage( int index, ProductHandler productHandler){
        this.index = index;
        this.productHandler = productHandler;
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

        productHandler.getProductsStore().get(index).setImage(image);
        productHandler.refreshStoreView();

        System.out.println("Download de imagem com sucesso!");

    }
}