package com.stone.desafiomobile.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.stone.desafiomobile.model.Product

/**
 * Classe que fornece acesso no banco a tabela de [Product]
 */
@Dao
interface ProductDAO {

    /**
     * Insere [Product] no banco
     * @param data produtos para inserir
     * @return lista com os ids dos produtos inseridos
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: Product): LongArray

    /**
     * Insere uma lista de [Product] no banco
     * @param data produtos para inserir
     * @return lista com os ids dos produtos inseridos
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBatch(data: List<Product>): LongArray

    /**
     * Recupera os [Product] do banco
     * @return lista de produtos
     */
    @Query("SELECT * FROM products")
    fun listAll(): LiveData<List<Product>>

    /**
     * Recupera um [Product] pelo id
     * @param title titulo do produto
     * @param seller vendedor do produto
     * @return Produto encontrado
     */
    @Query("SELECT * FROM products where title=:arg0 and seller=:arg1")
    fun findById(title: String, seller: String): LiveData<Product>
}
