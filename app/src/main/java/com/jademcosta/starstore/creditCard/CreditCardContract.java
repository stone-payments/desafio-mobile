package com.jademcosta.starstore.creditCard;


import com.jademcosta.starstore.entity.CreditCard;
import com.jademcosta.starstore.entity.Payment;

public interface CreditCardContract {

    interface View {

        String getCreditCardNumber();
        String getCreditCardOwnerName();
        String getCreditCardExpirationDate();
        String getCreditCardCvv();
        void showError();
        void hideLoading();
        void showLoading();

        interface Presenter {
            void sendButtonClicked();
        }
    }

    interface Model {

        void payWithCreditCard(CreditCard creditCard);

        interface Presenter {
            void paymentSuccessful(Payment payment);
            void paymentFailed();
        }
    }
}
