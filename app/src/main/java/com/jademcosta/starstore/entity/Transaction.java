package com.jademcosta.starstore.entity;


public class Transaction {

    public static final int NUMBER_OF_CARD_DIGITS = 4;

    private String name;
    private String creditCardLastFourDigits;
    private String value;
    private String dateTime;

    public Transaction(String name, String creditCardLastFourDigits, String value, String dateTime) {
        this.name = name;
        this.creditCardLastFourDigits = creditCardLastFourDigits;
        this.value = value;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditCardLastFourDigits() {
        return creditCardLastFourDigits;
    }

    public void setCreditCardLastFourDigits(String creditCardLastFourDigits) {
        this.creditCardLastFourDigits = creditCardLastFourDigits;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
