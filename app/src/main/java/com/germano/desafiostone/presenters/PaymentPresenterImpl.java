package com.germano.desafiostone.presenters;

import android.content.Context;

import com.germano.desafiostone.models.History;
import com.germano.desafiostone.models.Payment;
import com.germano.desafiostone.services.HistoryRealm;
import com.germano.desafiostone.services.HistoryRealmImpl;
import com.germano.desafiostone.services.PaymentService;
import com.germano.desafiostone.services.PaymentServiceImpl;
import com.germano.desafiostone.views.PaymentView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by germano on 28/08/17.
 */

public class PaymentPresenterImpl implements PaymentPresenter{

    private PaymentView mView;
    private PaymentService mService;
    HistoryRealm mRealm;

    public PaymentPresenterImpl(Context context) {
        mService = new PaymentServiceImpl();
        mRealm = new HistoryRealmImpl(context);
    }

    @Override
    public void initView(PaymentView paymentView) {
        this.mView = paymentView;

        if(mView != null){
            mView.contentView();
        }
    }

    @Override
    public void onPaymentClicked() {
        mView.callPaymentService();
    }

    @Override
    public void doPaymentService(Payment payment) {
        mView.showLoading();
        Observable<Payment> mObservableProduct = mService.doPayment(payment);

        mObservableProduct.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        retorno -> {
                            mView.hideLoading();
                            if (mView == null) return;
                            mView.showSuccessfulPayment();
                            mRealm.save(buildHistory(payment));
                        },
                        error -> {
                            mView.hideLoading();
                            //mView.showError(mContext.getString(R.string.error));
                        });
    }

    private History buildHistory(Payment payment){
        History history = new History();
        history.setValue(payment.getValue());
        history.setCardNumber(payment.getCardNumber().substring(12, 16));
        history.setHoldName(payment.getCardHolderName());
        return history;
    }
}
