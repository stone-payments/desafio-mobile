package com.example.leonardo.desafiomobile;

/**
 * Created by Leonardo on 20/04/2017.
 */

public class Transação {
    private String nCartão;
    private double valor;
    private int cvv;
    private String nome_Titular;
    private String vencimento;

    public Transação(String nCartão, double valor, int cvv, String nome_Titular, String vencimento){
        this.nCartão=nCartão;
        this.valor=valor;
        this.cvv=cvv;
        this.nome_Titular=nome_Titular;
        this.vencimento=vencimento;
    }
    public Transação(){
        this.nCartão ="";
        this.valor = 0;
        this.cvv = 0;
        this.nome_Titular = "";
        this.vencimento = "";
    }

    public String getnCartão() {
        return nCartão;
    }

    public void setnCartão(String nCartão) {
        this.nCartão = nCartão;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getNome_Titular() {
        return nome_Titular;
    }

    public void setNome_Titular(String nome_Titular) {
        this.nome_Titular = nome_Titular;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }
}
