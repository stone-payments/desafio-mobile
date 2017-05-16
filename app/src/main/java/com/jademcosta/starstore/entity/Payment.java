package com.jademcosta.starstore.entity;


public class Payment {

    private String ownerName;
    private String cvv;
    private String expirationDate;
    private String cardNumber;
    private String value;

    public Payment(String ownerName, String cvv, String expirationDate, String cardNumber, String value) {
        this.ownerName = ownerName;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.cardNumber = cardNumber;
        this.value = value;
    }

    public Payment(CreditCard creditCard, String value) {
        this.ownerName = creditCard.getOwnerName();
        this.cvv = creditCard.getCvv();
        this.expirationDate = creditCard.getExpirationDate();
        this.cardNumber = creditCard.getCardNumber();
        this.value = value;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLastDigitsFromCardNumber(int amount) {
        return cardNumber.substring(cardNumber.length() - amount, cardNumber.length());
    }
}
