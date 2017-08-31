//
//  Purchase.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 28/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import Foundation

struct Purchase {
    
    let title: String
    let price: Int
    let thumbnail: String
    var quantity: Int
    var total: Int
    
    init (title: String, price: Int, thumbnail: String, quantity: Int, total: Int) {
        self.title = title
        self.price = price
        self.thumbnail = thumbnail
        self.quantity = quantity
        self.total = total
    }
}
