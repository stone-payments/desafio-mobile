package br.com.valdir.desafiolojastarwars;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Carrinho {

    Map<Long, Integer> mCarrinho;
    //Map<ItemProduto, Integer> mProduto;
    double mValue = 0;

    Carrinho() {
        mCarrinho = new LinkedHashMap<>();
    }

    void addToCart(ItemProduto itemProduto) {
        if(mCarrinho.containsKey(itemProduto.getId()))
            mCarrinho.put(itemProduto.getId(), mCarrinho.get(itemProduto.getId()) + 1);
        else
            mCarrinho.put(itemProduto.getId(), 1);

        mValue += itemProduto.getPrice();
    }

    void removeFromCart(ItemProduto itemProduto) {

        if((mCarrinho.containsKey(itemProduto.getId())) && (mCarrinho.get(itemProduto.getId()).intValue() != 0)) {
            mCarrinho.put(itemProduto.getId(), mCarrinho.get(itemProduto.getId()) - 1);
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

