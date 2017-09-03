//
//  Product.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 27/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import Foundation

struct Product {
    
    let title: String
    let price: Int
    let zipCode: String
    let seller: String
    let thumbnail: String
    let date: String
    
    init (title: String, price: Int, zipCode: String, seller: String, thumbnail: String, date: String) {
        self.title = title
        self.price = price
        self.zipCode = zipCode
        self.seller = seller
        self.thumbnail = thumbnail
        self.date = date
    }
}
