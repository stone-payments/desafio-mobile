//
//  Card.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 26/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import Foundation

class Card {
    public var holderName: String!
    public var number: Int!
    public var cvv: Int!
    public var expirationDate: Date!
    
    init(holderName: String, number: Int, cvv: Int, expirationDate: Date) {
        self.holderName = holderName
        self.number = number
        self.cvv = cvv
        self.expirationDate = expirationDate
    }
    
    /// Create a string describing the card's expiration date.
    ///
    /// - Returns: String description of the expiration date (e.g. 01/01).
    public func expDateDescription() -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = "MM/yy"
        
        return formatter.string(from: expirationDate)
    }
}
