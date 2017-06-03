package com.partiufast.mercadodoimperador

class BillHistory(val map: MutableMap<String, Any?>){

    var _id: Long by map
    var date: String by map
    var customerName: String by map
    var cardLastNumbers: Int by map
    var value: String by map

    constructor(date: String, customerName: String, cardLastNumbers: Int, value: String): this(HashMap()){
        this.date = date
        this.customerName = customerName
        this.cardLastNumbers = cardLastNumbers
        this.value = value
    }



}
