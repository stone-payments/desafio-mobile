package kelly.com.desafiostone.models;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by kelly on 29/11/17.
 */

public class FullTransaction {
    private String cardNumber;
    private String holderName;
    private Date expirationDate;
    private int cvv;
    private double value;

    public FullTransaction() {}

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getFormatedDate(){
        if(getExpirationDate()==null) return null;

        DateFormat df = new SimpleDateFormat("MM/yy");
        String reportDate = df.format(getExpirationDate());

        return reportDate;
    }

    public HashMap<String, String> toHashMap () {
        HashMap<String, String> fullTransactionsHashMap = new HashMap<>();

        fullTransactionsHashMap.put("card_number", getCardNumber());
        fullTransactionsHashMap.put("value", Double.toString(getValue() * 100));
        fullTransactionsHashMap.put("cvv", Integer.toString(getCvv()));
        fullTransactionsHashMap.put("card_holder_name", getHolderName());
        fullTransactionsHashMap.put("exp_date", getFormatedDate());

        return fullTransactionsHashMap;
    }
}
