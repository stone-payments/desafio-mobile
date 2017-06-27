package com.example.pharol.temminstore.shoppingcart

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.pharol.temminstore.R
import com.example.pharol.temminstore.payment.PaymentInfoActivity
import com.example.pharol.temminstore.util.toMoneyString
import com.example.pharol.temminstore.TemminApplication
import com.example.pharol.temminstore.di.ui.DaggerFragmentComponent
import com.example.pharol.temminstore.di.ui.FragmentModule
import javax.inject.Inject


class ShoppingCartActivityFragment : LifecycleFragment(), ShoppingCartRecyclerAdapter.onButtonsClickListener {

    val component by lazy {
        DaggerFragmentComponent.builder().fragmentModule(FragmentModule(this)).
                applicationComponent((this.context.applicationContext as TemminApplication).applicationComponent).
                build()
    }

    @Inject lateinit var shoppingCartViewModelFactory : ShoppingCartViewModelFactory

    lateinit var shoppingCartView: ShoppingCartViewModel

    var recyclerView: RecyclerView? = null
    var total: TextView? = null
    var adapter = ShoppingCartRecyclerAdapter(arrayListOf(),this)

    override fun onClickMinus(cart: ShoppingCart) {
        shoppingCartView.minusItems(cart)
    }

    override fun onClickPlus(cart: ShoppingCart) {
        shoppingCartView.plusItem(cart)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): android.view.View? {
        val returnView = inflater.inflate(R.layout.fragment_shopping_cart, container, false)

        recyclerView = returnView.findViewById(R.id.rv_shopping_cart_view) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = adapter
        total = returnView.findViewById(R.id.tv_cart_price) as TextView


        val bt_buy = returnView.findViewById(R.id.bt_buy) as android.widget.Button
        bt_buy.setOnClickListener({
            startActivity(android.content.Intent(this.context, PaymentInfoActivity::class.java))
        })

        val bt_clearCart = returnView.findViewById(R.id.bt_clear_cart) as android.widget.Button
        bt_clearCart.setOnClickListener({
            shoppingCartView.clearCart()
        })
        return returnView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shoppingCartView = ViewModelProviders.of(this, shoppingCartViewModelFactory).get(ShoppingCartViewModel::class.java)
        
        shoppingCartView.getCartTotal().observe(this, Observer {
            total?.text = it?.toMoneyString()
        })

        shoppingCartView.getLastCart().observe(this, Observer {
            if (it != null && !it.isEmpty()) {
                adapter.shptCartItemList = it
                adapter.notifyDataSetChanged()
            }else{
                this.activity.finish()
            }
        })
    }

}
