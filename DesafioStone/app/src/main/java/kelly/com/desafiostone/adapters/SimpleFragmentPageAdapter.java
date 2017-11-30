package kelly.com.desafiostone.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kelly.com.desafiostone.fragments.FragmentCart;
import kelly.com.desafiostone.fragments.FragmentHome;
import kelly.com.desafiostone.fragments.FragmentInsertTransaction;

/**
 * Created by kelly on 30/11/17.
 */

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {
    private FragmentHome fragmentHome;
    private FragmentCart fragmentCart;
    private FragmentInsertTransaction fragmentInsertTransaction;


    public SimpleFragmentPageAdapter(FragmentManager fm) {
        super(fm);

        fragmentHome = new FragmentHome();
        fragmentCart = new FragmentCart();
        fragmentInsertTransaction = new FragmentInsertTransaction();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return fragmentCart;
            case 2:
                return fragmentInsertTransaction;
            default:
                return fragmentHome;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
