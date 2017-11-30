package kelly.com.desafiostone.activities;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import kelly.com.desafiostone.adapters.SimpleFragmentPageAdapter;
import kelly.com.desafiostone.R;
import kelly.com.desafiostone.models.FullTransaction;
import kelly.com.desafiostone.network.QueryUtils;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        SimpleFragmentPageAdapter simpleFragmentPageAdapter = new SimpleFragmentPageAdapter(getSupportFragmentManager());
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

    public class Teste extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            FullTransaction fullTransaction = new FullTransaction();
            fullTransaction.setCardNumber("1234123412341234");
            fullTransaction.setCvv(123);
            fullTransaction.setValue(22.80);
            fullTransaction.setHolderName("Kelly M Bentes");
            fullTransaction.setExpirationDate(new Date(11/22));

            String urlString = getString(R.string.post_transactions_url);

            try {
                QueryUtils.sendTransaction(urlString, fullTransaction);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
