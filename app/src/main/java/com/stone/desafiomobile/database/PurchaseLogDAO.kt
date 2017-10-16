package com.stone.desafiomobile.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.stone.desafiomobile.model.PurchaseLog

/**
 * Classe que fornece acesso no banco a tabela de [PurchaseLog]
 */
@Dao
interface PurchaseLogDAO {

    /**
     * Insere [PurchaseLog] no banco
     * @param data logs para inserir
     * @return lista com os ids dos logs inseridos
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg data: PurchaseLog): LongArray

    /**
     * Recupera os [PurchaseLog] do banco
     * @return lista de logs
     */
    @Query("SELECT * FROM purchase_logs order by date desc")
    fun listAll(): LiveData<List<PurchaseLog>>

    /**
     * Recupera um [PurchaseLog] pelo id
     * @param id id do log
     * @return log encontrado
     */
    @Query("SELECT * FROM purchase_logs where id=:arg0")
    fun findById(id: Long): LiveData<PurchaseLog>
}