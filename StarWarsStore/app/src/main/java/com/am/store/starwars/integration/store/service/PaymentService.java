package com.am.store.starwars.integration.store.service;

import com.am.store.starwars.StarWarStoreActivity;
import com.am.store.starwars.exception.StarWarServiceException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.integration.store.vo.request.payment.PaymentRequestVO;
import com.am.store.starwars.model.store.product.Purchase;

/**
 * Created by Augusto on 15/01/2017.
 */

public class PaymentService {

    private static final String LOG_CONSTANT = PaymentService.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    public Purchase performPayment(PaymentRequestVO paymentRequestVO) throws StarWarServiceException {

        try {

            return null;
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to perform payment!", e);
            throw new StarWarServiceException("Problems to perform payment!", e);
        }
    }
}