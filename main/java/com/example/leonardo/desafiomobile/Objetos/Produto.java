package com.example.leonardo.desafiomobile.Objetos;

/**
 * Created by Leonardo on 02/04/2017.
 */

public class Produto {
    private String title ;
    private String price;
    private String zipcode ;
    private String seller;
    private String thumbnailHd;
    private String date;
    private String quantidade;

    public Produto(String title, String price, String zipcode, String seller, String thumbnailHd, String date,String quantidade) {
        this.title = title;
        this.price = price;
        this.zipcode = zipcode;
        this.seller = seller;
        this.thumbnailHd = thumbnailHd;
        this.date = date;
        this.quantidade = quantidade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return Double.toString(Double.parseDouble(price)/100);
    }

    public String getPrice1(){
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getSeller() {
        return ("Vendedor(a): " + seller);
    }

    public String getSeller1(){
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getThumbnailHd() {
        return thumbnailHd;
    }

    public void setThumbnailHd(String thumbnailHd) {
        this.thumbnailHd = thumbnailHd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setQuantidade(String quantidade){
        this.quantidade = quantidade;
    }

    public String getQuantidade(){
        return quantidade;}
}
