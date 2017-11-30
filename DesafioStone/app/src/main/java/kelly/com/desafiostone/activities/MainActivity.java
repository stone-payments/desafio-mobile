package kelly.com.desafiostone.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import kelly.com.desafiostone.loaders.ItensLoader;
import kelly.com.desafiostone.R;
import kelly.com.desafiostone.models.FullTransaction;
import kelly.com.desafiostone.models.Item;
import kelly.com.desafiostone.network.QueryUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Item>>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isInternetConnectionAvailable()){
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(1, null, this);

            new Teste().execute();
        }
    }

    private boolean isInternetConnectionAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable()))
            return true;
        else
            return false;
    }

    @Override
    public Loader<ArrayList<Item>> onCreateLoader(int id, Bundle args) {

        ItensLoader itensLoader = new ItensLoader(this, getString(R.string.get_itens_url));
        return itensLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Item>> loader, ArrayList<Item> data) {
        // TODO make loader onLoadFinished
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Item>> loader) {
        // TODO make loader onLoaderReset
    }

    public class Teste extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            FullTransaction fullTransaction = new FullTransaction();
            fullTransaction.setCardNumber("1234123412341234");
            fullTransaction.setCvv(123);
            fullTransaction.setValue(22.80);
            fullTransaction.setHolderName("Kelly M Bentes");
            fullTransaction.setExpirationDate(new Date(11/22));

            String urlString = getString(R.string.post_transactions_url);

            try {
                QueryUtils.sendTransaction(urlString, fullTransaction);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
