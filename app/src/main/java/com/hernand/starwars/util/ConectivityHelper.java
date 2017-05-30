package com.hernand.starwars.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Nando on 27/05/2017.
 * Helper usado para verificar se existe conexão
 */
public class ConectivityHelper {

    private Context ctx;

    public ConectivityHelper(Context ctx){
        this.ctx = ctx;
    }

    /**
     * Verifica se existe conexão
     * @return
     */
    public Boolean isConected() {

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected())
            return  true;


        return false;
    }
}
