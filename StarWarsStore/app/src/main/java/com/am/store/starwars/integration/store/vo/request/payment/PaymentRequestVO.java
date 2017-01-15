package com.am.store.starwars.integration.store.vo.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Augusto on 15/01/2017.
 */

public class PaymentRequestVO {

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("value")
    private int amount;

    @JsonProperty("cvv")
    private int cvv;

    @JsonProperty("card_holder_name")
    private String cardHolderName;

    @JsonProperty("exp_date")
    private String expDate;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
}