package victorcruz.dms;

import android.graphics.Bitmap;

public class Produto {
    private String titulo;
    private int preco;
    private String cep;
    private String vendedor;
    private Bitmap imagem;
    private String data;

    public Produto(String titulo, int preco, String cep, String vendedor, Bitmap imagem, String data) {
        this.titulo = titulo;
        this.preco = preco;
        this.cep = cep;
        this.vendedor = vendedor;
        this.imagem = imagem;
        this.data = data;
    }

    public Produto(Produto produto) {
        this.titulo = produto.getTitulo();
        this.preco = produto.getPreco();
        this.cep = produto.getCep();
        this.vendedor = produto.getVendedor();
        this.imagem = produto.getImagem();
        this.data = produto.getData();
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPreco() {
        return preco;
    }

    public String getCep(){
        return cep;
    }

    public String getVendedor(){
        return vendedor;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
