package br.com.tiago.desafiomobile.presenter.impl;

import android.util.Log;

import java.util.List;

import br.com.tiago.desafiomobile.interactor.TransactionInteractor;
import br.com.tiago.desafiomobile.model.TransactionModel;
import br.com.tiago.desafiomobile.presenter.CartPresenter;
import br.com.tiago.desafiomobile.ui.fragment.CartListFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TiagoCabral on 8/23/2017.
 */

public class CartPresenterImpl implements CartPresenter {

    private static String TAG = CartPresenterImpl.class.getSimpleName();
    private CartListFragment cartListFragment;
    private TransactionInteractor transactionInteractor;

    public CartPresenterImpl(CartListFragment cartListFragment, TransactionInteractor transactionInteractor) {
        this.cartListFragment = cartListFragment;
        this.transactionInteractor = transactionInteractor;
    }

    @Override
    public void insertTransaction(TransactionModel transactionModel) {
        transactionInteractor.insertTransaction(transactionModel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        cartListFragment.cleanCartList();
                        cartListFragment.setTransactionModel(new TransactionModel());
                        cartListFragment.showToast("Compra finalizada com sucesso!", 0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        cartListFragment.showToast("Erro ao finalizar compra!", 0);
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Log.d(TAG, "onNext: ");
                    }
                });
    }

}
