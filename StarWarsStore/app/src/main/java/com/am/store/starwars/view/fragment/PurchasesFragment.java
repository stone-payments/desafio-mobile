package com.am.store.starwars.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.am.store.starwars.R;
import com.am.store.starwars.core.PurchasesManager;
import com.am.store.starwars.exception.StarWarsException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.model.store.product.Purchase;
import com.am.store.starwars.view.adapter.PurchaseViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PurchasesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchasesFragment extends Fragment {

    private static final String LOG_TAG = PurchasesFragment.class.getSimpleName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    @BindView(R.id.purchases_listOfPurchases)
    protected ListView purchasesListView;

    @BindView(R.id.purchasest_txtNoItems)
    protected TextView txtNoPurchases;

    private PurchasesManager purchasesManager;

    public PurchasesFragment() {
        this.purchasesManager = new PurchasesManager();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static PurchasesFragment newInstance() {
        PurchasesFragment fragment = new PurchasesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currentLayout = inflater.inflate(R.layout.fragment_purchases, container, false);
        ButterKnife.bind(this, currentLayout);

        try {
            PurchaseViewAdapter adapter = new PurchaseViewAdapter(getActivity().getApplicationContext(), purchasesManager.puchases());
            purchasesListView.setAdapter(adapter);
        } catch (Exception e) {
            //TODO:
        }

        return currentLayout;
    }

    @Override
    public void onResume() {
        showHideViews();
        super.onResume();
    }

    private void showHideViews() {
        try {
            List<Purchase> purchases = purchasesManager.puchases();

            if (purchases != null && (!purchases.isEmpty())) {
                txtNoPurchases.setVisibility(View.GONE);
                purchasesListView.setVisibility(View.VISIBLE);
            } else {
                txtNoPurchases.setVisibility(View.VISIBLE);
                purchasesListView.setVisibility(View.GONE);
            }
        } catch (StarWarsException e) {
            txtNoPurchases.setVisibility(View.VISIBLE);
            purchasesListView.setVisibility(View.GONE);
        }
    }
}