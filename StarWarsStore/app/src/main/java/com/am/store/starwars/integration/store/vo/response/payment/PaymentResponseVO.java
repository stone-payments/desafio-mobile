package com.am.store.starwars.integration.store.vo.response.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Augusto on 15/01/2017.
 */

public class PaymentResponseVO implements Serializable {

    @JsonProperty("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}