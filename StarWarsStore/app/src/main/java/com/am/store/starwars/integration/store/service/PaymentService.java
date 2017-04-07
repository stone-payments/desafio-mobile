package com.am.store.starwars.integration.store.service;

import android.os.AsyncTask;

import com.am.store.starwars.communication.http.RestServiceBuilder;
import com.am.store.starwars.core.ShoppingCartManager;
import com.am.store.starwars.dao.PurchaseDAO;
import com.am.store.starwars.exception.StarWarPersistenceException;
import com.am.store.starwars.exception.StarWarServiceException;
import com.am.store.starwars.helper.AndroidLogger;
import com.am.store.starwars.integration.store.action.PaymentAction;
import com.am.store.starwars.integration.store.vo.request.payment.PaymentRequestVO;
import com.am.store.starwars.integration.store.vo.response.payment.PaymentResponseVO;
import com.am.store.starwars.model.store.product.Purchase;

import retrofit2.Call;

/**
 * Created by Augusto on 15/01/2017.
 */
public class PaymentService {

    private static final String LOG_CONSTANT = PaymentService.class.getName();
    private static final AndroidLogger logger = AndroidLogger.getInstance();

    private PurchaseDAO purchaseDAO;
    private ShoppingCartManager shoppingCartManager;

    public PaymentService() {
        this.purchaseDAO = new PurchaseDAO();
        this.shoppingCartManager = new ShoppingCartManager();
    }

    public void performPayment(PaymentRequestVO paymentRequestVO) throws StarWarServiceException {

        try {
            sendPayment(paymentRequestVO);
        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to perform payment!", e);
            throw new StarWarServiceException("Problems to perform payment!", e);
        }
    }

    private void sendPayment(final PaymentRequestVO paymentRequestVO) throws StarWarServiceException {

        try {
            RestServiceBuilder restBuilder = new RestServiceBuilder("http://private-8001d4-starwarstore.apiary-mock.com", PaymentAction.class);
            PaymentAction paymentAction = null;
            paymentAction = (PaymentAction) restBuilder.build();

            new AsyncTask<Void, Void, PaymentResponseVO>() {

                @Override
                protected PaymentResponseVO doInBackground(Void... params) {
                    try {
                        RestServiceBuilder restBuilder = new RestServiceBuilder("http://private-8001d4-starwarstore.apiary-mock.com", PaymentAction.class);
                        PaymentAction paymentAction = (PaymentAction) restBuilder.build();
                        Call<PaymentResponseVO> paymentCall = paymentAction.performPayment(paymentRequestVO);
                        PaymentResponseVO responseVO = paymentCall.execute().body();
                        return responseVO;
                    } catch (Exception e) {
                        logger.error(LOG_CONSTANT, "Problems to send payment", e);
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(final PaymentResponseVO paymentResponseVO) {
                    if (paymentRequestVO != null) {
                        try {
                            Purchase purchase = createPurchase(paymentRequestVO);
                            shoppingCartManager.clearShoppingCart();
                        } catch (Exception e) {
                            logger.error(LOG_CONSTANT, "Problems to persist payment", e);
                        }
                    }
                }
            }.execute();

        } catch (Exception e) {
            logger.error(LOG_CONSTANT, "Problems to perform payment!", e);
            throw new StarWarServiceException("Problems to perform payment!", e);
        }
    }

    private Purchase createPurchase(PaymentRequestVO paymentRequestVO) throws StarWarPersistenceException {

        Purchase purchase = new Purchase();
        purchase.setAmount(paymentRequestVO.getAmount());
        purchase.setCardHolder(paymentRequestVO.getCardHolderName());
        purchase.setDateTime(System.currentTimeMillis());
        purchase.setLastDigitsCardNumber(paymentRequestVO.getLastDigitsOfCardNumber());

        purchaseDAO.insertPurchase(purchase);

        return purchase;
    }

    private interface Callback<T> {
        public void receive(T t);
    }
}