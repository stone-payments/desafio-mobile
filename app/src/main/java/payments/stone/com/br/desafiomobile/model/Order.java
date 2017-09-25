package payments.stone.com.br.desafiomobile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by william.gouvea on 9/25/17.
 */

public class Order {
    @SerializedName("card_number")
    private String cardNumber;
    private String value;
    private String cvv;
    @SerializedName("card_holder_name")
    private String cardHolder;
    @SerializedName("exp_date")
    private String expDate;

    public String getCardNumber() {
        return cardNumber;
    }

    public Order cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Order value(String value) {
        this.value = value;
        return this;
    }

    public String getCvv() {
        return cvv;
    }

    public Order cvv(String cvv) {
        this.cvv = cvv;
        return this;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public Order cardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
        return this;
    }

    public String getExpDate() {
        return expDate;
    }

    public Order expDate(String expDate) {
        this.expDate = expDate;
        return this;
    }
}
