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
    let orderValue: String
    let cardCVV: String
    var holderName: String
    var expireDate: String
    
    init (cardNumber: String, orderValue: String, cardCVV: String, holderName: String, expireDate: String) {
        self.cardNumber = cardNumber
        self.orderValue = orderValue
        self.cardCVV = cardCVV
        self.holderName = holderName
        self.expireDate = expireDate
    }
}
