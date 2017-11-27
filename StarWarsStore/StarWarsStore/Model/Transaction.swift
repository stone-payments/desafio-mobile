//
//  Transaction.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 26/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import Foundation

class Transaction {
    public var value: Double!
    public var dateTime: Date!
    public var card: Card!
    
    init(value: Double, dateTime: Date, card: Card) {
        self.value = value
        self.dateTime = dateTime
        self.card = card
    }
    
    /// Prepare a JSON to send a POST request.
    ///
    /// - Returns: JSON form of the transaction.
    public func getPostForm() -> [String : Any] {
        /// Example of JSON Transaction
        //  {
        //      "card_number":"1234123412341234",
        //      "value":7990,
        //      "cvv":789,
        //      "card_holder_name":"Luke Skywalker",
        //      "exp_date":"12/24"
        //  }
        
        var jsonTransaction = [String : Any]()
        
        if let validCardNumber = card.number { _ = jsonTransaction.updateValue(validCardNumber, forKey: "card_number") }
        if let validValue = value { _ = jsonTransaction.updateValue(validValue, forKey: "value") }
        if let validCvv = card.cvv { _ = jsonTransaction.updateValue(validCvv, forKey: "cvv") }
        if let validCardHolder = card.holderName { _ = jsonTransaction.updateValue(validCardHolder, forKey: "card_holder_name") }
         _ = jsonTransaction.updateValue(card.expDateDescription(), forKey: "exp_date")
        
        if JSONSerialization.isValidJSONObject(jsonTransaction) {
            return jsonTransaction
        }
        
        return [:]
    }
}
