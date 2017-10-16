package com.stone.desafiomobile.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.stone.desafiomobile.R
import com.stone.desafiomobile.di.DaggerInjectionComponent
import com.stone.desafiomobile.di.DatabaseModule
import com.stone.desafiomobile.di.RetrofitModule
import com.stone.desafiomobile.model.Product
import com.stone.desafiomobile.viewmodel.ProductsListVm

/**
 * Fragment contendo a lista de produtos
 */
class ProductsListFragment : Fragment(), ProductsListAdapter.ItemClickCallback {

    lateinit internal var mViewModel: ProductsListVm
    lateinit internal var mRecyclerView: RecyclerView
    lateinit internal var mAdapter: ProductsListAdapter

    lateinit internal var mBuyButton: Button
    lateinit internal var mEmptyListTV: TextView
    lateinit internal var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(ProductsListVm::class.java)

        //injeta as dependencias
        val injectionComponent = DaggerInjectionComponent.builder()
                .retrofitModule(RetrofitModule())
                .databaseModule(DatabaseModule(activity))
                .build()
        injectionComponent.inject(mViewModel)


        mViewModel.loadProducts()

        // observa os produtos que estao no banco de dados para atualizar a lista
        mViewModel.products.observe(this, Observer<List<Product>> { products ->
            if (products != null) {
                mAdapter.mValues = products
                if (!products.isEmpty()) {
                    mEmptyListTV.visibility = View.GONE
                }
            }
        })


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_products_list, container, false)

        mRecyclerView = view.findViewById(R.id.products_list)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter = ProductsListAdapter(this, mViewModel.cartItens)
        mRecyclerView.adapter = mAdapter

        mBuyButton = view.findViewById(R.id.buy_button)
        mBuyButton.setOnClickListener { buyProduct() }

        // mostra o botão de comprar se algum item estiver no carrinho
        if (!mViewModel.cartItens.isEmpty()) {
            mBuyButton.visibility = View.VISIBLE
        }

        mEmptyListTV = view.findViewById(R.id.empty_list_text)

        mSwipeRefreshLayout = view.findViewById(R.id.swipeContainer)
        mSwipeRefreshLayout.setOnRefreshListener { onRefresh() }

        return view
    }

    /**
     * Recupera produtos da api e para o refresh no fim da execução
     */
    fun onRefresh() {
        mViewModel.loadProducts({ mSwipeRefreshLayout.isRefreshing = false })
    }

    /**
     * Adiciona um item no carrinho
     * @param product [Product] a ser adicionado no carrinho
     */
    override fun addToCart(product: Product) {
        mViewModel.cartItens.add(product)
        mBuyButton.visibility = View.VISIBLE
    }


    /**
     * Adiciona um item no carrinho
     * @param product [Product] a ser removido do carrinho
     */
    override fun removeFromCart(product: Product) {
        mViewModel.cartItens.remove(product)

        // se nao sobrou itens no carrinho faz o botao de comprar desaparecer
        if (mViewModel.cartItens.isEmpty()) {
            mBuyButton.visibility = View.GONE
        }
    }

    /**
     * Leva para [CheckoutActivity] passando o valor da compra
     */
    fun buyProduct() {
        val intent = Intent(activity, CheckoutActivity::class.java)
        intent.putExtra(CheckoutActivity.ARG_CART, mViewModel.defineValue())
        startActivity(intent)
    }
}
