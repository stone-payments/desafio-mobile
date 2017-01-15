package com.am.store.starwars.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.am.store.starwars.R;
import com.am.store.starwars.core.ShoppingCartManager;
import com.am.store.starwars.exception.StarWarServiceException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.model.store.product.Product;
import com.am.store.starwars.view.activity.CheckoutActivity;
import com.am.store.starwars.view.adapter.ShoppingCartViewAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends Fragment {

    private static final String LOG_CONSTANT = ShoppingCartFragment.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    private ShoppingCartManager shoppingCartManager;

    public ShoppingCartFragment() {
        // Required empty public constructor
        shoppingCartManager = new ShoppingCartManager();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShoppingCartFragment.
     */
    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutFragment = inflater.inflate(R.layout.shoppingcart_layout, container, false);

        ListView listView = (ListView) layoutFragment.findViewById(R.id.shoppingCart_listOfProducts);
        Button btnBuy = (Button) layoutFragment.findViewById(R.id.shoppingCart_btnBuy);
        ViewGroup layoutCheckout = (ViewGroup) layoutFragment.findViewById(R.id.shoppingCart_items);
        TextView lblNoItems = (TextView) layoutFragment.findViewById(R.id.shoppingCart_txtNoItems);

        try {
            List<Product> products = shoppingCartManager.getShoppingCart();

            if (products != null && (!products.isEmpty())) {
                layoutCheckout.setVisibility(View.VISIBLE);
                lblNoItems.setVisibility(View.GONE);

                ShoppingCartViewAdapter adapter = new ShoppingCartViewAdapter(getContext().getApplicationContext(), products);
                listView.setAdapter(adapter);

                btnBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentCheckout = new Intent(getActivity(), CheckoutActivity.class);
                        startActivity(intentCheckout);
                    }
                });
            } else {
                layoutCheckout.setVisibility(View.GONE);
                lblNoItems.setVisibility(View.VISIBLE);
            }
        } catch (StarWarServiceException e) {
            logger.error(LOG_CONSTANT, "Problems to build view with ShoppingCart Items", e);
        }

        return layoutFragment;
    }
}