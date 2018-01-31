package br.com.valdir.desafiolojastarwars.api;

/**
 * Created by valdir on 30/01/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Infor {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("resultado")
    @Expose
    private String resultado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}