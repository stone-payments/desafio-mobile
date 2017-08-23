package br.com.tiago.desafiomobile.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import br.com.tiago.desafiomobile.ui.fragment.CartListFragment;
import br.com.tiago.desafiomobile.ui.fragment.ProductListFragment;
import br.com.tiago.desafiomobile.ui.fragment.TransactionFragment;

/**
 * Created by TiagoCabral on 8/21/2017.
 */

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static String TAG = MyFragmentPagerAdapter.class.getSimpleName();
    private String[] tabTitles;

    public MyFragmentPagerAdapter(FragmentManager fm, String[] tabTitles) {
        super(fm);
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Log.d(TAG, "getItem: CASE 0");
                return new ProductListFragment();

            case 1:
                Log.d(TAG, "getItem: CASE 1");
                return new CartListFragment();

            case 2:
                Log.d(TAG, "getItem: CASE 2");
                return new TransactionFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return this.tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.tabTitles[position];
    }


}
