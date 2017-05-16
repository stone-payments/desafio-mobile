package com.jademcosta.starstore.entity;


public class CreditCard {

    private String ownerName;
    private String cvv;
    private String expirationDate;
    private String cardNumber;

    public CreditCard(String ownerName, String cvv, String expirationDate, String cardNumber) {
        this.ownerName = ownerName;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.cardNumber = cardNumber;
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
}
