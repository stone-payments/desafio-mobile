package com.germano.desafiostone.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by germano on 28/08/17.
 */

public class History extends RealmObject {

    private int value;

    @PrimaryKey
    private String timestamp;

    private String cardNumber;

    private String holdName;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHoldName() {
        return holdName;
    }

    public void setHoldName(String holdName) {
        this.holdName = holdName;
    }

}
