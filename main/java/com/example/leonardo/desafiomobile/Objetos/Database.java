package com.example.leonardo.desafiomobile.Objetos;

/**
 * Created by Leonardo on 21/04/2017.
 */

public class Database {

    private double valor;
    private String deh;
    private int ultimosDig;
    private String Nome;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDeh() {
        return deh;
    }

    public void setDeh(String deh) {
        this.deh = deh;
    }

    public int getUltimosDig() {
        return ultimosDig;
    }

    public void setUltimosDig(int ultimosDig) {
        this.ultimosDig = ultimosDig;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Database(){
        this.valor = 0;
        this.deh = "";
        this.ultimosDig=0;
        this.Nome="";
    }



}
