package com.hernand.starwars.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nando on 28/05/2017.
 */

public class TransactionVO implements Serializable {

    @SerializedName("value")
    private Long valor;

    @SerializedName("cvv")
    private Long cvv;

    @SerializedName("card_number")
    private String numeroCartao;// ############9999

    @SerializedName("card_holder_name")
    private String nomePortador;

    @SerializedName("exp_date")
    private String expDate;


    private transient String dataHoraTransacao;

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setCvv(Long cvv) {
        this.cvv = cvv;
    }

    public Long getCvv() {
        return cvv;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNomePortador() {
        return nomePortador;
    }

    public void setNomePortador(String nomePortador) {
        this.nomePortador = nomePortador;
    }

    @Override
    public String toString() {
        return "TransactionVO{" +
                "valor='" + valor + '\'' +
                ", cvv='" + cvv + '\'' +
                ", numeroCartao='" + numeroCartao + '\'' +
                ", nomePortador='" + nomePortador + '\'' +
                ", expDate='" + expDate + '\'' +
                '}';
    }

    public String getDataHoraTransacao() {
        return dataHoraTransacao;
    }

    public void setDataHoraTransacao(String dataHoraTransacao) {
        this.dataHoraTransacao = dataHoraTransacao;
    }
}
