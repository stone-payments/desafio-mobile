package com.stone.lfernandosantos.storewars.controlers;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by lf.fernandodossantos on 20/05/17.
 */

public class SupportStatus {

    Context context;

    public SupportStatus(Context context){
        this.context = context;
    }

    public Boolean getStatusInternet() {
        Boolean aBoolean;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()){

            aBoolean = true;

        }else {
            aBoolean = false;
        }
        return aBoolean;
    }
}
