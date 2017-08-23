package br.com.tiago.desafiomobile.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by TiagoCabral on 8/18/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true, value = {"date", "valueDouble"})
public class TransactionModel {

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("card_holder_name")
    private String cardHolderName;

    @JsonProperty("exp_date")
    private String expDate;

    private Long value;
    private Integer cvv;

    private String date;
    private Double valueDouble;


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public void setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
    }
}
