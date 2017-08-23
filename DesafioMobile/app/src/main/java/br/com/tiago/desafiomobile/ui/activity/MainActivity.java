package br.com.tiago.desafiomobile.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import br.com.tiago.desafiomobile.R;
import br.com.tiago.desafiomobile.ui.adapter.MyFragmentPagerAdapter;
import br.com.tiago.desafiomobile.ui.fragment.TransactionFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //private FragmentManager fm = getSupportFragmentManager();

    private static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.toolbar_main_title);
        toolbar.setSubtitle(R.string.toolbar_main_subtitle);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.main_tabs)));
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: ");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);

                switch (position) {
                    case 0:
                        toolbar.setSubtitle(R.string.toolbar_main_subtitle);
                        break;

                    case 1:
                        toolbar.setSubtitle(R.string.toolbar_cart_subtitle);
                        break;

                    case 2:
                        toolbar.setSubtitle(R.string.toolbar_transaction_subtitle);
                        TransactionFragment.getTransactions();
                        break;

                    default:
                        return;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: ");
            }
        });


//        if (savedInstanceState == null) {
//            fm.beginTransaction()
//                    .add(R.id.fragment_container, new ProductListFragment(), "ProductListFragment")
//                    .addToBackStack("stack")
//                    .setAllowOptimization(true)
//                    .commit();
//        }
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

}
