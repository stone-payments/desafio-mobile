package kelly.com.desafiostone.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import kelly.com.desafiostone.interfaces.ComunicatorFragmentActivity;
import kelly.com.desafiostone.R;
import kelly.com.desafiostone.adapters.ItemAdapter;
import kelly.com.desafiostone.loaders.ItensLoader;
import kelly.com.desafiostone.models.Item;

public class FragmentHome extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Item>>{

    private ListView mItemList;
    private static final int ITEM_LOADER = 1;
    private ComunicatorFragmentActivity mComunicatorFragmentActivity;
    private ItemAdapter.ListAdapterListener mListAdapterListener;

    public FragmentHome() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListAdapterListener = new ItemAdapter.ListAdapterListener() {
            @Override
            public void onClickAtItem(Item item) {
                mComunicatorFragmentActivity.addItemToCart(item);
            }
        };

        mItemList = (ListView) view.findViewById(R.id.lv_itens_list);
        ItemAdapter itemAdapter = new ItemAdapter(getContext(), new ArrayList<Item>(), mListAdapterListener);
        mItemList.setAdapter(itemAdapter);

        if (isInternetConnectionAvailable()){
            LoaderManager loaderManager = getActivity().getSupportLoaderManager();
            loaderManager.initLoader(ITEM_LOADER, null, this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mComunicatorFragmentActivity = (ComunicatorFragmentActivity) getContext();
    }

    private boolean isInternetConnectionAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable()))
            return true;
        else
            return false;
    }

    @Override
    public Loader<ArrayList<Item>> onCreateLoader(int id, Bundle args) {

        ItensLoader itensLoader = new ItensLoader(getContext(), getString(R.string.get_itens_url));
        return itensLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Item>> loader, ArrayList<Item> data) {
        if(data != null){
            ItemAdapter itemAdapter = new ItemAdapter(getContext(), data, mListAdapterListener);
            mItemList.setAdapter(itemAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Item>> loader) {
        ItemAdapter itemAdapter = new ItemAdapter(getContext(), new ArrayList<Item>(), mListAdapterListener);
        mItemList.setAdapter(itemAdapter);
    }
}
