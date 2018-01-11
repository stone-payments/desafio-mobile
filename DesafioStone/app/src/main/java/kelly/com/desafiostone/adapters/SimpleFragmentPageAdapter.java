package kelly.com.desafiostone.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kelly.com.desafiostone.fragments.FragmentCart;
import kelly.com.desafiostone.fragments.FragmentHome;
import kelly.com.desafiostone.fragments.FragmentListTransactions;

/**
 * Created by kelly on 30/11/17.
 */

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {
    private FragmentHome mFragmentHome;
    private FragmentCart mFragmentCart;
    private FragmentListTransactions mFragmentListTransactions;


    public SimpleFragmentPageAdapter(FragmentManager fm, FragmentCart fragmentCart) {
        super(fm);

        mFragmentHome = new FragmentHome();
        mFragmentCart = fragmentCart;
        mFragmentListTransactions = new FragmentListTransactions();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return mFragmentCart;
            case 2:
                return mFragmentListTransactions;
            default:
                return mFragmentHome;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
