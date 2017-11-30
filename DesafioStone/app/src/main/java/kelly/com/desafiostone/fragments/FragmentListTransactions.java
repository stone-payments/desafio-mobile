package kelly.com.desafiostone.fragments;


import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import kelly.com.desafiostone.R;
import kelly.com.desafiostone.adapters.TransactionsCursorAdapter;
import kelly.com.desafiostone.data.TransactionContract;

public class FragmentListTransactions extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private ListView mTransactionsList;
    private TransactionsCursorAdapter mTransactionsCursorAdapter;
    private static final int TRANSACTION_LOADER = 2;

    public FragmentListTransactions() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_transactions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTransactionsList = (ListView) view.findViewById(R.id.lv_transactions_list);
        mTransactionsCursorAdapter = new TransactionsCursorAdapter(getContext(), null);
        mTransactionsList.setAdapter(mTransactionsCursorAdapter);

        getActivity().getSupportLoaderManager().initLoader(TRANSACTION_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                TransactionContract.TransactionEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mTransactionsCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTransactionsCursorAdapter.swapCursor(null);
    }
}
