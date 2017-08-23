package br.com.tiago.desafiomobile.presenter.impl;

import android.util.Log;

import java.util.List;

import br.com.tiago.desafiomobile.interactor.TransactionInteractor;
import br.com.tiago.desafiomobile.model.TransactionModel;
import br.com.tiago.desafiomobile.presenter.TransactionPresenter;
import br.com.tiago.desafiomobile.ui.fragment.TransactionFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TiagoCabral on 8/23/2017.
 */

public class TransactionPresenterImpl implements TransactionPresenter {

    private static String TAG = TransactionPresenterImpl.class.getSimpleName();
    private TransactionFragment transactionFragment;
    private TransactionInteractor transactionInteractor;

    public TransactionPresenterImpl(TransactionFragment transactionFragment, TransactionInteractor transactionInteractor) {
        this.transactionFragment = transactionFragment;
        this.transactionInteractor = transactionInteractor;
    }

    @Override
    public void getTransactions() {
        transactionInteractor.getTransactions()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TransactionModel>>() {

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ", e);
                        transactionFragment.showToast("Erro ao carregar histórico de transações!", 0);
                    }

                    @Override
                    public void onNext(List<TransactionModel> transactionModels) {
                        transactionFragment.addAll(transactionModels);
                    }
                });
    }
}
