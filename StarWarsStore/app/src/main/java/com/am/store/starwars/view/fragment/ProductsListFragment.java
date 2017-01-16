package com.am.store.starwars.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.am.store.starwars.R;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.integration.store.action.ProductAction;
import com.am.store.starwars.communication.http.RestServiceBuilder;
import com.am.store.starwars.integration.store.vo.ProductVO;
import com.am.store.starwars.view.adapter.ProductViewAdapter;
import com.am.store.starwars.view.fragment.swipe.SwipeRefreshListFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Augusto on 13/01/2017.
 */

public class ProductsListFragment extends SwipeRefreshListFragment {

    private static final String LOG_TAG = ProductsListFragment.class.getSimpleName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setColorScheme(R.color.color_scheme_1_1, R.color.color_scheme_1_2,
                R.color.color_scheme_1_3, R.color.color_scheme_1_4);

        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                logger.info(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                initiateRefresh();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initiateRefresh();
    }


    private void initiateRefresh() {
        Log.i(LOG_TAG, "initiateRefresh");
        try {
            RestServiceBuilder restBuilder = new RestServiceBuilder("https://raw.githubusercontent.com", ProductAction.class);
            ProductAction productAction = (ProductAction) restBuilder.build();

            Call<List<ProductVO>> response = productAction.getProducts();
            response.enqueue(new Callback<List<ProductVO>>() {
                @Override
                public void onResponse(Call<List<ProductVO>> call, Response<List<ProductVO>> response) {

                    logger.info(LOG_TAG, "funcionou");
                    List<ProductVO> productVOs = response.body();

                    onRefreshComplete(productVOs);
                    ProductViewAdapter adapter = new ProductViewAdapter(getContext(), productVOs);

                    setListAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<ProductVO>> call, Throwable t) {
                    logger.error(LOG_TAG, "nao funcionou!!!!!!!!!!!!!!!!!!!!!");
                }
            });

        } catch (Exception e) {
            logger.error(LOG_TAG, e);
        }
    }

    private void onRefreshComplete(List<ProductVO> result) {
        logger.info(LOG_TAG, "onRefreshComplete");
        setRefreshing(false);

        Collections.sort(result, new Comparator<ProductVO>() {

            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public int compare(ProductVO o1, ProductVO o2) {
                try {
                    return f.parse(o1.getDate()).compareTo(f.parse(o2.getDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }
}