package jb.project.starwarsshoppinglist.cart.listener

/**
 * Created by joao.neto on 16/10/2017.
 */
 interface ItemClickListener {
    fun onClick(title : String)

    fun onSpinnerSelected(title: String, amount: Int)
}