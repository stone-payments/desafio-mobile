//
//  Order.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 31/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import Foundation

struct Order {
    
    let cardNumber: String
    let orderValue: Int
    let cardCVV: String
    var holderName: String
    var expireDate: String
    var orderDate: Date
    
    init (cardNumber: String, orderValue: Int, cardCVV: String, holderName: String, expireDate: String, orderDate: Date) {
        self.cardNumber = cardNumber
        self.orderValue = orderValue
        self.cardCVV = cardCVV
        self.holderName = holderName
        self.expireDate = expireDate
        self.orderDate = orderDate
    }
}
