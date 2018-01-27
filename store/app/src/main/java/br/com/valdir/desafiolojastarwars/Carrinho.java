package br.com.valdir.desafiolojastarwars;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Carrinho {

    Map<ItemProduto, Integer> mCarrinho;
    double mValue = 0;

    Carrinho() {
        mCarrinho = new LinkedHashMap<>();
    }

    void addToCart(ItemProduto itemProduto) {
        if(mCarrinho.containsKey(itemProduto))
            mCarrinho.put(itemProduto, mCarrinho.get(itemProduto) + 1);
        else
            mCarrinho.put(itemProduto, 1);

        mValue += itemProduto.getPrice();
    }

    void removeFromCart(ItemProduto itemProduto) {

        if((mCarrinho.containsKey(itemProduto)) && (mCarrinho.get(itemProduto).intValue() != 0)) {
            mCarrinho.put(itemProduto, mCarrinho.get(itemProduto) - 1);
            mValue -= itemProduto.getPrice();
        }

        if((mCarrinho.containsKey(itemProduto)) && (mCarrinho.get(itemProduto).intValue() == 0))
            mCarrinho.remove(itemProduto);

    }

    int getQuantity(ItemProduto itemProduto)
    {
        return mCarrinho.get(itemProduto);
    }

    Set getProducts()
    {
        return mCarrinho.keySet();
    }

    void empty()
    {
        mCarrinho.clear();
        mValue = 0;
    }

    double getValue()
    {
        return mValue;
    }

    int getSize()
    {
        return mCarrinho.size();
    }
}

