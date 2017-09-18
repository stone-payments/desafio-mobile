package victorcruz.dms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import victorcruz.dms.UI.ExpandableHeightListView;
import victorcruz.dms.UI.PaymentDialogFragment;
import victorcruz.dms.get_post_data.GetJSON;
import victorcruz.dms.product.ProductHandler;
import victorcruz.dms.transaction.TransactionsHandler;

public class MainActivity extends AppCompatActivity {

    // Android UI
    private ScrollView storeScrollView;
    private ScrollView cartScrollView;
    private ScrollView transactionScrollView;
    private LinearLayout cartToolbarLinearLayout;
    private TextView ToolbarTitleTextView;

    private ProductHandler productHandler;
    private TransactionsHandler transactionsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Android UI
        ExpandableHeightListView storeListView = (ExpandableHeightListView) findViewById(R.id.storeListView);
        ExpandableHeightListView cartListView = (ExpandableHeightListView) findViewById(R.id.cartListView);
        ExpandableHeightListView transactionListView = (ExpandableHeightListView) findViewById(R.id.transactionListView);
        storeScrollView = (ScrollView) findViewById(R.id.storeScrollView);
        cartScrollView = (ScrollView) findViewById(R.id.cartScrollView);
        transactionScrollView = (ScrollView) findViewById(R.id.transactionScrollView);
        cartToolbarLinearLayout = (LinearLayout) findViewById(R.id.cartToolbarLinearLayout);
        ToolbarTitleTextView = (TextView) findViewById(R.id.toolbarTitleTextView);
        TextView cartTotalValueTextView = (TextView) findViewById(R.id.cartTotalValueTextView);

        // mantem controle dos produtos e transacoes
        productHandler = new ProductHandler(storeListView, cartListView, cartTotalValueTextView, this);
        transactionsHandler = new TransactionsHandler(this, transactionListView);


        GetJSON getJSON = new GetJSON(productHandler);
        getJSON.execute();

    }

    public void showPaymentDialog(View view){

        if(productHandler.getCartTotalValue() == 0){
            Toast.makeText(this, "O seu carrinho está vazio!", Toast.LENGTH_SHORT).show();
        } else {
            //PaymentDialogFragment paymentDialogFragment = PaymentDialogFragment.newInstance(null);
            PaymentDialogFragment paymentDialogFragment = new PaymentDialogFragment();
            paymentDialogFragment.setArguments(productHandler, transactionsHandler);
            paymentDialogFragment.show(getSupportFragmentManager(), null);
        }

    }

    public void addToCart(View view){
        productHandler.addToCart(view);
    }

    public void removeFromCart(View view){
        productHandler.removeFromCart(view);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ToolbarTitleTextView.setText("Loja");
                    storeScrollView.setVisibility(View.VISIBLE);
                    cartScrollView.setVisibility(View.INVISIBLE);
                    transactionScrollView.setVisibility(View.INVISIBLE);
                    cartToolbarLinearLayout.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    ToolbarTitleTextView.setText("Carrinho");
                    storeScrollView.setVisibility(View.INVISIBLE);
                    cartScrollView.setVisibility(View.VISIBLE);
                    transactionScrollView.setVisibility(View.INVISIBLE);
                    cartToolbarLinearLayout.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    ToolbarTitleTextView.setText("Transações");
                    storeScrollView.setVisibility(View.INVISIBLE);
                    cartScrollView.setVisibility(View.INVISIBLE);
                    transactionScrollView.setVisibility(View.VISIBLE);
                    cartToolbarLinearLayout.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        }

    };

}
