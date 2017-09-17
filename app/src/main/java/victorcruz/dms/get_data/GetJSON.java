package victorcruz.dms.get_data;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import victorcruz.dms.UI.ExpandableHeightListView;
import victorcruz.dms.produto.Product;
import victorcruz.dms.produto.ProductSoreAdapter;

public class GetJSON extends AsyncTask<String, Void, String> {

    private GetImage getImage;
    private ArrayList<Product> productsStore;

    private ExpandableHeightListView StoreListView;
    private ProductSoreAdapter productSoreAdapter;


    public GetJSON(ArrayList<Product> productsStore, ExpandableHeightListView StoreListView,
                   ProductSoreAdapter productSoreAdapter){
        this.productsStore = productsStore;

        this.StoreListView = StoreListView;
        this.productSoreAdapter = productSoreAdapter;
    }

    @Override
    protected String doInBackground(String... params) {

        URL url;
        HttpURLConnection httpURLConnection;
        InputStream inputStream = null;

        // Vai receber byte streams e decodificar em char
        InputStreamReader inputStreamReader;

        String result = "";

        try {

            url = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            int data = inputStreamReader.read();

            // registra char por char em result
            while (data != -1) {
                char current = (char) data;

                result += current;

                data = inputStreamReader.read();
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        System.out.print("Resultado: " + result);

        try {
            JSONArray jsonArray = new JSONArray(result);

            System.out.println(productsStore.size());

            for (int i = 0; i < jsonArray.length(); i++) {
                // separa o json em partes por item_store
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // baixa a imagem de cada item_store
                getImage = new GetImage(productsStore, i, StoreListView, productSoreAdapter);
                getImage.execute(jsonObject.getString("thumbnailHd"));

                // cria cada item_store
                productsStore.add(new Product(
                        jsonObject.getString("title"),
                        jsonObject.getInt("price"),
                        jsonObject.getString("zipcode"),
                        jsonObject.getString("seller"),
                        null,
                        jsonObject.getString("date")));


                System.out.println("Product eh: " + i + " " + productsStore.get(i).getTitle());
            }

            System.out.println(productsStore.size());

            // atualiza a view
            StoreListView.setAdapter(productSoreAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
