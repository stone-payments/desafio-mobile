package com.hernand.starwars.model;
import com.orm.SugarRecord;
import java.io.Serializable;

/**
 * Created by Nando on 28/05/2017.
 * Classe mapeada com sugar para o sqlite
 * Documentação http://satyan.github.io/sugar/getting-started.html
 */
public class Transact extends SugarRecord<Transact> implements Serializable {


    //id automaticamente gerado pelo sugar
    private String valor;
    private String dataHora; //dd/MM/yyyy HH:MM:ss
    private String numeroCartao;// ############9999
    private String nomePortador;

    public Transact(){

    }

    public Transact(String valor, String dataHora, String numeroCartao, String nomePortador) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.numeroCartao = numeroCartao;
        this.nomePortador = nomePortador;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
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
}
