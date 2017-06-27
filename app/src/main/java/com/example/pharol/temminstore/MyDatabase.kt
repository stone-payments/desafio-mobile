package com.example.pharol.temminstore

//import com.example.pharol.temminstore.util.BigDecimalConverter
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.pharol.temminstore.shoppingcart.ShoppingCart
import com.example.pharol.temminstore.shoppingcart.ShoppingCartDao
import com.example.pharol.temminstore.transaction.Transaction
import com.example.pharol.temminstore.transaction.TransactionDAO
import com.example.pharol.temminstore.util.BigDecimalConverter
import com.example.pharol.temminstore.util.TimeStampConverter
import com.example.pharol.temminstore.item.Item
import com.example.pharol.temminstore.item.ItemDAO

@Database(entities = arrayOf(ShoppingCart::class, Item::class, Transaction::class), version = 1)
@TypeConverters(TimeStampConverter::class,BigDecimalConverter::class)
abstract class MyDatabase : RoomDatabase(){
    abstract fun shoppingCartDao(): ShoppingCartDao
    abstract fun itemDao(): ItemDAO
    abstract fun transactionDao(): TransactionDAO

}


