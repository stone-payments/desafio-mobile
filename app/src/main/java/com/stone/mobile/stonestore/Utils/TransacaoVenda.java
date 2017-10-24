package com.stone.mobile.stonestore.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransacaoVenda {

    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("value")
    @Expose
    private int value;
    @SerializedName("cvv")
    @Expose
    private int cvv;
    @SerializedName("card_holder_name")
    @Expose
    private String cardHolderName;
    @SerializedName("exp_date")
    @Expose
    private String expDate;

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
