//
//  User.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 26/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import Foundation

class User {
    public var name: String!
    public var cards: [Card]!
    public var cart: [Item]!
    
    init(name: String) {
        self.name = name
        cards = []
        cart = []
    }
    
    public func addCard(_ newCard: Card) {
        cards.append(newCard)
    }
    
    /// Calculate the total price of the user's current cart.
    ///
    /// - Returns: Cart's total price.
    public func totalCartPrice() -> Double {
        var totalPrice = 0.0
        
        for item in cart {
            totalPrice += item.price
        }
        
        return totalPrice
    }
}
