package br.com.valdir.desafiolojastarwars.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by valdyrtorres on 05/12/2017.
 */

public class ProdutosAuthenticatorService extends Service {

    private ProdutosAuthenticator produtosAuthenticator;

    @Override
    public void onCreate() {
        produtosAuthenticator = new ProdutosAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return produtosAuthenticator.getIBinder();
    }
}
