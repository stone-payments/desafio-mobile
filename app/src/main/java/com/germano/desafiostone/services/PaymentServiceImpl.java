package com.germano.desafiostone.services;

import com.germano.desafiostone.models.Payment;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by germano on 28/08/17.
 */

public class PaymentServiceImpl implements PaymentService {
    @Override
    public Observable<Payment> doPayment(Payment payment) {
        IPaymentService service = RetrofitUtil.getInstance().apiaryBuild().create(IPaymentService.class);

        return service.doPayment(payment)
                .observeOn(Schedulers.io())
                .map(response -> {
                    if (response != null) {
                        return response;
                    }
                    return null;
                });
    }
}
