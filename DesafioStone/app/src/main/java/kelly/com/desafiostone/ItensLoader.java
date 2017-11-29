package kelly.com.desafiostone;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by kelly on 29/11/17.
 */

public class ItensLoader extends AsyncTaskLoader<ArrayList<Item>> {
    String url;

    public ItensLoader (Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Item> loadInBackground() {
        if (url == null) {
            return null;
        }
        ArrayList itensList = QueryUtils.getItensData(url);
        return itensList;
    }
}
