package br.com.tiago.desafiomobile.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.tiago.desafiomobile.R;
import br.com.tiago.desafiomobile.interactor.impl.TransactionInteractorImpl;
import br.com.tiago.desafiomobile.model.TransactionModel;
import br.com.tiago.desafiomobile.presenter.TransactionPresenter;
import br.com.tiago.desafiomobile.presenter.impl.TransactionPresenterImpl;
import br.com.tiago.desafiomobile.ui.adapter.TransactionRecyclerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TiagoCabral on 8/22/2017.
 */

public class TransactionFragment extends BaseFragment {

    private static String TAG = TransactionFragment.class.getSimpleName();
    private TransactionRecyclerAdapter transactionRecyclerAdapter;
    private static TransactionPresenter transactionPresenter;

    @BindView(R.id.transaction_recycler_view)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: inicio");
        View inflate;

        inflate = inflater.inflate(R.layout.transaction_history_fragment, container, false);
        ButterKnife.bind(this, inflate);

        transactionRecyclerAdapter = new TransactionRecyclerAdapter();
        recyclerView.setAdapter(transactionRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        transactionPresenter = new TransactionPresenterImpl(this, new TransactionInteractorImpl(getContext()));

        return inflate;
    }


    public static void getTransactions() {
        transactionPresenter.getTransactions();
    }

    public void addAll(List<TransactionModel> transactionModels) {
        transactionRecyclerAdapter.addAll(transactionModels);
    }
}
