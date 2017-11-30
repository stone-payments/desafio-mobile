package kelly.com.desafiostone.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import kelly.com.desafiostone.fragments.FragmentCart;
import kelly.com.desafiostone.interfaces.ComunicatorActivityFragment;
import kelly.com.desafiostone.interfaces.ComunicatorFragmentActivity;
import kelly.com.desafiostone.adapters.SimpleFragmentPageAdapter;
import kelly.com.desafiostone.R;
import kelly.com.desafiostone.models.FullTransaction;
import kelly.com.desafiostone.models.Item;
import kelly.com.desafiostone.network.QueryUtils;

public class MainActivity extends AppCompatActivity implements ComunicatorFragmentActivity{

    private ArrayList<Item> mListItensAdded;
    private ViewPager mViewPager;
    private ComunicatorActivityFragment mComunicatorActivityFragment;
    private FragmentCart mFragmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListItensAdded = new ArrayList<>();

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mFragmentCart = new FragmentCart();

        SimpleFragmentPageAdapter simpleFragmentPageAdapter = new SimpleFragmentPageAdapter(getSupportFragmentManager(), mFragmentCart);
        mViewPager.setAdapter(simpleFragmentPageAdapter);

        ImageView imageViewHome = (ImageView) findViewById(R.id.img_vw_home);
        imageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        ImageView imageViewCart = (ImageView) findViewById(R.id.img_vw_cart);
        imageViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mComunicatorActivityFragment = (ComunicatorActivityFragment) mFragmentCart;
                mComunicatorActivityFragment.setListItensAdded(mListItensAdded);
                mViewPager.setCurrentItem(1);
            }
        });

        ImageView imageViewTransactionList = (ImageView) findViewById(R.id.img_vw_transactions);
        imageViewTransactionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });
    }

    @Override
    public void addItemToCart(Item item) {
        mListItensAdded.add(item);
    }

    @Override
    public void procedToCheckout() {

        double total = getTotal(mListItensAdded);

        mListItensAdded.clear();

        Bundle bundle = new Bundle();
        bundle.putDouble("total", total);

        Intent intent = new Intent(this, InsertTransactionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private double getTotal (ArrayList<Item> itensList){
        double total = 0;

        for (Item item : itensList){
            total += item.getPrice();
        }

        return total;
    }
}
