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
    
    private let PREFIX: String = "Cart-"
    
    static let shared = CartManager()
    
    var userDefaults = UserDefaults.standard
    
    func addToCart(object: Product, key: String) {
        guard let jsonString = Mapper().toJSONString(object, prettyPrint: true) else { return }
        UserDefaults.standard.set(jsonString, forKey: "\(PREFIX)\(key)")
    }
    
    func getAllCarts() -> [Product]? {
        let keys = userDefaults.dictionaryRepresentation().keys.filter({ $0.hasPrefix(PREFIX) })
        return keys.flatMap({ getFromCart(key: $0) })
    }
    
    func getFromCart(key: String) -> Product? {
        guard let jsonString = UserDefaults.standard.value(forKey: key) as? String else { return nil }
        return Mapper<Product>().map(JSONString: jsonString)
    }
    
    func deleteFromCart(key: String) {
        UserDefaults.standard.removeObject(forKey: "\(PREFIX)\(key)")
    }
}
