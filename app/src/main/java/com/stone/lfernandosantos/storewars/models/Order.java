package com.stone.lfernandosantos.storewars.models;

import java.io.Serializable;

/**
 * Created by lf.fernandodossantos on 22/05/17.
 */

public class Order implements Serializable {

    public long idOrder;
    public String date;
    public Double total;
    public String itens;

    public String getPrice() {

        String priceFormat = String.format("%.2f",total);
        return priceFormat;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", date='" + date + '\'' +
                ", total=" + total +
                '}';
    }
}
