package com.desafiostone.domain;

import io.realm.RealmObject;

/**
 * Created by Filipi Andrade on 19-Oct-17.
 */

public class Transaction extends RealmObject {

    private String value;
    private String date_hour;
    private String card_holder_name;
    private String last_digits;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate_hour() {
        return date_hour;
    }

    public void setDate_hour(String date_hour) {
        this.date_hour = date_hour;
    }

    public String getCard_holder_name() {
        return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public String getLast_digits() {
        return last_digits;
    }

    public void setLast_digits(String last_digits) {
        this.last_digits = last_digits;
    }
}
