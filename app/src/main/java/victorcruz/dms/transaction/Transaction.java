package victorcruz.dms.transaction;


public class Transaction {

    private int value;
    private String cardName;
    private String cardNumber;
    private String date;

    public Transaction(int value, String cardName, String cardNumber, String date) {
        this.value = value;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.date = date;
    }

    public Transaction(Transaction transaction) {
        this.value = transaction.getValue();
        this.cardName = transaction.getCardName();
        this.cardNumber = transaction.getCardNumber();
        this.date = transaction.getDate();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCardName(){
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
