package payments.stone.com.br.desafiomobile.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by william.gouvea on 9/25/17.
 */

public class Order extends RealmObject implements Comparable<String> {
    private static int objId = 0;

    @PrimaryKey
    private int id;

    @SerializedName("card_number")
    private String cardNumber;
    private String value;
    private String cvv;
    @SerializedName("card_holder_name")
    private String cardHolder;
    @SerializedName("exp_date")
    private String expDate;

    @SerializedName("transaction_date")
    private String transactionDate;

    public Order() {
        id = objId++;
    }

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

    public String formattedTransactionDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date(transactionDate));
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void transactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public int compareTo(@NonNull String o) {
        return 0;
    }
}
