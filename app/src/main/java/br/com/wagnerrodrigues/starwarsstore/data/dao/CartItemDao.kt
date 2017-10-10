package br.com.wagnerrodrigues.starwarsstore.data.dao

import br.com.wagnerrodrigues.starwarsstore.data.helper.DatabaseHelper
import br.com.wagnerrodrigues.starwarsstore.domain.entity.CartItem
import br.com.wagnerrodrigues.starwarsstore.presentation.BaseApp
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager

class CartItemDao() {

    lateinit var daoManager : Dao<CartItem, Int>

    companion object {
        var dao : CartItemDao? = null

        fun  getInstance() : CartItemDao?{
            if(dao == null){
                dao = CartItemDao()
                dao?.daoManager = DaoManager.createDao(DatabaseHelper.getInstance(BaseApp.getContext())?.connectionSource, CartItem::class.java)
            }

            return dao;
        }
    }

    fun fetchAll() : List<CartItem>? {
        return daoManager.queryForAll();
    }

    fun delete(cartItem: CartItem) {
        daoManager.delete(cartItem)
    }

    fun create(cartItem: CartItem) {
        daoManager.create(cartItem)
    }

    fun clear() {
        daoManager.deleteBuilder().delete()
    }
}