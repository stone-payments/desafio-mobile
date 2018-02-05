package br.com.valdir.desafiolojastarwars;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/* {
      "title": "Blusa do Imperio",
      "price": 7990,
      "zipcode": "78993-000",
      "seller": "Jo\u00e3o da Silva",
      "thumbnailHd": "https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg",
      "date": "26/11/2015"
    } */

public class ItemProduto implements Serializable {

    private long id;
    private String title;
    private Double price;
    private String zipcode;
    private String seller;
    private String thumbnailHd;
    private String date;

    public ItemProduto(long id, String title, Double price, String zipcode, String seller, String thumbnailHd, String date) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.zipcode = zipcode;
        this.seller = seller;
        this.thumbnailHd = thumbnailHd;
        this.date = date;
    }

    public ItemProduto(JSONObject jsonObject) throws JSONException {
        Random r = new Random();

        // TODO Procurar um gerador de ID mais confiável
        this.id = r.nextInt(10000+1);

        this.title = jsonObject.getString("title");
        this.price = jsonObject.getDouble("price");
        this.zipcode = jsonObject.getString("zipcode");
        this.seller = jsonObject.getString("seller");
        this.thumbnailHd = jsonObject.getString("thumbnailHd");
        this.date = jsonObject.getString("date");
    }

    public ItemProduto(int seedId, JSONObject jsonObject) throws JSONException {

        this.id = seedId;

        this.title = jsonObject.getString("title");
        this.price = jsonObject.getDouble("price");
        this.zipcode = jsonObject.getString("zipcode");
        this.seller = jsonObject.getString("seller");
        this.thumbnailHd = jsonObject.getString("thumbnailHd");
        this.date = jsonObject.getString("date");
    }

    private String buildPath(String width, String path) {
        StringBuilder builder = new StringBuilder();
        builder.append("http://")
               .append(width)
               .append(path);

        return builder.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getSeller() {
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

        Locale locale = new Locale("pt", "BR");

        try {
            Date dateAux = new SimpleDateFormat("dd/MM/yyyy", locale).parse(date);
            return new SimpleDateFormat("dd/MM/yyyy", locale).format(dateAux);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
