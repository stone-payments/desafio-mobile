package com.hernand.starwars.util;

import com.hernand.starwars.vo.ProductVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nando on 28/05/2017.
 */

public class CartSingleton {
    private static final CartSingleton ourInstance = new CartSingleton();

    public static CartSingleton getInstance() {
        return ourInstance;
    }

    private List<ProductVO> produtos = new ArrayList<>();

    private CartSingleton() {
    }

    public List<ProductVO> getProdutos() {
        return produtos;
    }
}
