package com.luiztorres.stone.starwarsshop.extras;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Dindal on 06/01/2017.
 */

public class Order {
    private Double value;
    private String date;
    private String lastNubmers;
    private String name;

    public Order(Double value, String date, String lastNubmers, String name) {
        this.value = value;
        this.date = date;
        this.lastNubmers = lastNubmers;
        this.name = name;
    }

    public Order(){}

    public Double getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public String getLastNubmers() {
        return lastNubmers;
    }

    public String getName() {
        return name;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLastNubmers(String lastNubmers) {
        this.lastNubmers = lastNubmers;
    }

    public void setName(String name) {
        this.name = name;
    }
}
