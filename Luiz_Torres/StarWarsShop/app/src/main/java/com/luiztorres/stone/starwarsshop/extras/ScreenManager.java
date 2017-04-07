package com.luiztorres.stone.starwarsshop.extras;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.luiztorres.stone.starwarsshop.R;

/**
 * Created by Dindal on 03/01/2017.
 */

public class ScreenManager {
    private static ScreenManager mInstance;
    private static Context ctx;
    FragmentManager fm;
    private Fragment actual = new Fragment();

    private ScreenManager(Context c) {
        this.ctx = c;
        fm = ((FragmentActivity) ctx).getFragmentManager();
    }

    public static synchronized ScreenManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ScreenManager(context);
        }
        return mInstance;
    }

    public void Replace(Fragment fr)
    {
        if(!actual.getClass().equals(fr.getClass())) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_place, fr);
            transaction.commit();
            actual = fr;
        }
    }

    public void Add(Fragment fr)
    {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_place, fr);
        transaction.commit();
    }

    public Fragment getActual()
    {
        return actual;
    }
}
