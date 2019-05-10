package br.com.stone.vianna.starstore.view.shoppingCart

import android.os.Bundle
import br.com.stone.vianna.starstore.R
import br.com.stone.vianna.starstore.baseClasses.BaseActivity

class ShoppingCartActivity : BaseActivity(), ShoppingCartContract.View {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
    }


}