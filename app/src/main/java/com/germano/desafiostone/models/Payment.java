package com.germano.desafiostone.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by germano on 28/08/17.
 */

public class Payment implements Serializable{

    @SerializedName("card_number")
    String cardNumber;

    int value;

    int cvv;

    @SerializedName("card_holder_name")
    String cardHolderName;

    @SerializedName("exp_date")
    String expDate;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
