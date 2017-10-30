package com.example.victo.desafiostone;

/**
 * Created by victo on 27/10/2017.
 */

public class ItensBanco {

    private String nome;
    private String price;
    private String cartao;

    public ItensBanco ItensBanco(String price, String nome, String cartao) {
        ItensBanco itensBanco = new ItensBanco();
        itensBanco.setPrice(price);
        itensBanco.setNome(nome);
        itensBanco.setCartao(cartao);
        return itensBanco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }


}