package BD;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Transaction {

    private int id;
    private int value;
    private String date;
    private String hour;
    private String cardLastDigits;
    private String cardOwner;

    public Transaction(int value, String cardOwner, String cardLastDigits){
        this.setValue(value);
        this.setCardOwner(cardOwner);
        this.setCardLastDigits(cardLastDigits);
        this.setCurrentDateAndHour();
    }

    public Transaction(){}

    private void setCurrentDateAndHour(){
        GregorianCalendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);

        this.setDate(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(calendar.MONTH)+1) +
                "/" + calendar.get(calendar.YEAR));
        this.setHour((calendar.get(Calendar.HOUR_OF_DAY)) + ":" + calendar.get(Calendar.MINUTE));
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getCardLastDigits() {
        return cardLastDigits;
    }

    public void setCardLastDigits(String cardLastDigits) {
        this.cardLastDigits = cardLastDigits;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }
}
