package kelly.com.desafiostone.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kelly on 29/11/17.
 */

public class ShortTransaction {
    private int serverId;
    private String lastCardNumbers;
    private String holderName;
    private Date occurrenceDate;
    private double value;

    public ShortTransaction() {
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getLastCardNumbers() {
        return lastCardNumbers;
    }

    public void setLastCardNumbers(String lastCardNumbers) {
        this.lastCardNumbers = lastCardNumbers;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Date getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(Date occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
