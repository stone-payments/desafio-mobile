package kelly.com.desafiostone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Item>>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isInternetConnectionAvailable()){
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(1, null, this);
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

        ItensLoader itensLoader = new ItensLoader(this, getString(R.string.base_url));
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
}
