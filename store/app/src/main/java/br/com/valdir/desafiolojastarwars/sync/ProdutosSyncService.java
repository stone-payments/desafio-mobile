package br.com.valdir.desafiolojastarwars.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by valdyrtorres on 05/12/2017.
 */

public class ProdutosSyncService extends Service {

    private static ProdutosSyncAdapter produtosSyncAdapter = null;

    private static final Object lock = new Object();

    @Override
    public void onCreate() {
        // Para ser criada uma só vez e sincronizada
        synchronized (lock) {
            if (produtosSyncAdapter == null) {
                produtosSyncAdapter = new ProdutosSyncAdapter(getApplicationContext(),true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return produtosSyncAdapter.getSyncAdapterBinder();
    }
}
