//
//  CartManager.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//

import Foundation
import ObjectMapper


class CartManager {
    
    private let PREFIX  : String = "Cart-"
    private let SUFIX   : String = "-Shop"
    
    static let shared = CartManager()
    
    var userDefaults = UserDefaults.standard
    
    func addToCart(object: Product, key: String) {
        guard let jsonString = Mapper().toJSONString(object, prettyPrint: true) else { return }
        UserDefaults.standard.set(jsonString, forKey: "\(PREFIX)\(key)")
        userDefaults.synchronize()
    }
    
    func getAllCarts() -> [Product]? {
        let keys = userDefaults.dictionaryRepresentation().keys.filter({ $0.hasPrefix(PREFIX) })
        return keys.flatMap({ getFromCart(key: $0) })
    }
    
    func getFromCart(key: String) -> Product? {
        guard let jsonString = userDefaults.value(forKey: key) as? String else { return nil }
        return Mapper<Product>().map(JSONString: jsonString)
    }
    
    func deleteFromCart(key: String) {
        userDefaults.removeObject(forKey: "\(PREFIX)\(key)")
    }
    
    func buyCart(card: Card) {
        
        var total = 0
        _ = getAllCarts()?.map({ total += $0.price })
       
        let transaction = Transaction(card: card, value: total, date: Date())
        let key = "\(card.cardHolderName)-\(transaction.value.description)-\(transaction.date)\(SUFIX)"
        
        guard let jsonString = Mapper().toJSONString(transaction, prettyPrint: true) else { return }

        userDefaults.set(jsonString, forKey: key)
        userDefaults.synchronize()
    }
    
    func getAllTransactions() -> [Transaction]? {
        let keys = userDefaults.dictionaryRepresentation().keys.filter({ $0.hasSuffix(SUFIX) })
        return keys.flatMap({ getTransaction(key: $0) })
    }
    
    func getTransaction(key: String) -> Transaction? {
        guard let jsonString = userDefaults.value(forKey: key) as? String else { return nil }
        return Mapper<Transaction>().map(JSONString: jsonString)
    }
}
