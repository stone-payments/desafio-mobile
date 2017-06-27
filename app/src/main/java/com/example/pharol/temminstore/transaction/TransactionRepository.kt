package com.example.pharol.temminstore.transaction

import android.arch.lifecycle.LiveData
import android.content.Context
import android.widget.Toast
import com.example.pharol.temminstore.shoppingcart.ShoppingCartDao
import com.example.pharol.temminstore.WebService
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository
@Inject constructor(val webService: WebService,
                    val transactionDAO: TransactionDAO, val cartDAO: ShoppingCartDao, val context: Context){

    /**
     * Executes Payment on Server and Saves a Transaction on local DB
     */
    fun pay(transaction: Transaction){
        // savind only 4 last digits of credit card
        transaction.card_number = transaction.card_number.substring(15)
        webService.buyItem(transaction).enqueue(object: retrofit2.Callback<Transaction> {
            override fun onFailure(call: Call<Transaction>?, t: Throwable?) {
                Toast.makeText(context,"Error on payment! Please retry!",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Transaction>?, response: Response<Transaction>?) {
                Executors.newSingleThreadExecutor().execute({
                    transactionDAO.save(transaction)
                    cartDAO.delete()
                })
            }

        })
    }

    fun deleteAll(){
        Executors.newSingleThreadExecutor().execute({
            transactionDAO.deleteAll()
        })
    }

    fun getAll(): LiveData<List<Transaction>>{
        return transactionDAO.getAll()
    }
}